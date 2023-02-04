package com.cdg.buslinkbackend.service.ticket;

import com.cdg.buslinkbackend.exception.BusNotFoundException;
import com.cdg.buslinkbackend.exception.ClientNotFoundException;
import com.cdg.buslinkbackend.exception.TicketNotFoundException;
import com.cdg.buslinkbackend.model.entity.Bus;
import com.cdg.buslinkbackend.model.entity.Client;
import com.cdg.buslinkbackend.model.entity.Seating;
import com.cdg.buslinkbackend.model.entity.Ticket;
import com.cdg.buslinkbackend.model.enums.SeatingStatus;
import com.cdg.buslinkbackend.model.enums.TicketStatus;
import com.cdg.buslinkbackend.model.mappers.TicketMapper;
import com.cdg.buslinkbackend.model.request.ticket.TicketReceiptRequestDTO;
import com.cdg.buslinkbackend.model.request.ticket.TicketRequestDTO;
import com.cdg.buslinkbackend.repository.BusRepository;
import com.cdg.buslinkbackend.repository.ClientRepository;
import com.cdg.buslinkbackend.repository.TicketRepository;
import com.cdg.buslinkbackend.util.compressor.ImageCompressor;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import com.cdg.buslinkbackend.util.response.ResponseBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * It's a service class that implements the TicketService interface.
 */
@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    private final BusRepository busRepository;

    private final ResponseBuilder responseBuilder;

    private final ClientRepository clientRepository;

    // It's a constructor.
    public TicketServiceImpl(TicketRepository ticketRepository, BusRepository busRepository,
            ResponseBuilder responseBuilder, ClientRepository clientRepository) {
        this.ticketRepository = ticketRepository;
        this.busRepository = busRepository;
        this.responseBuilder = responseBuilder;
        this.clientRepository = clientRepository;
    }

    /**
     * The function receives a ticketRequestDTO object, which contains the bus
     * number, the client id
     * and the seatings that the client wants to buy. The function checks if the bus
     * exists, if the
     * client exists and if the seatings are available. If everything is ok, the
     * function saves the
     * ticket and returns a response
     * 
     * @param ticketRequestDTO
     * @return A ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> saveTicket(TicketRequestDTO ticketRequestDTO) {
        Bus bus = busRepository.findByBusNumber(ticketRequestDTO.getBusNumber())
                .orElseThrow(() -> new BusNotFoundException(ticketRequestDTO.getBusNumber().toString()));

        Client client = clientRepository.findById(ticketRequestDTO.getClient_id())
                .orElseThrow(() -> new ClientNotFoundException(ticketRequestDTO.getClient_id()));

        List<Seating> seatingList = bus.getSeating().stream().filter(
                seating -> ticketRequestDTO.getSeatings().contains(seating.getNumber())
                        && seating.getStatus().equals(SeatingStatus.LIBRE))
                .peek(seating -> seating.setStatus(SeatingStatus.OCUPADO)).toList();

        if (seatingList.isEmpty() || seatingList.size() != ticketRequestDTO.getSeatings().size()) {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(),
                    "El o los asientos no estan disponibles");
        }
        bus.getSeating().forEach(
                seating -> {
                    if (ticketRequestDTO.getSeatings().contains(seating.getNumber())
                            && seating.getStatus().equals(SeatingStatus.LIBRE)) {
                        seating.setStatus(SeatingStatus.OCUPADO);
                    }
                });
        busRepository.save(bus);
        Ticket ticket = TicketMapper.ticketFromTicketRequestDTO(ticketRequestDTO, seatingList);
        ticket.setStatus(TicketStatus.PENDIENTE);
        ticket.setCreatedAt(new Date());
        ticket.setCheck(false);
        ticket.setClient(client);
        ticket = ticketRepository.save(ticket);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Compra exitosa", ticket);
    }

    /**
     * It takes a byte array, compresses it, saves it to the database, then
     * decompresses it and returns
     * it
     * 
     * @param ticketReceiptRequestDTO This is the object that contains the image in
     *                                byte[] format.
     * @return A byte array.
     */
    @Override
    public ResponseEntity<ApiResponse> setReciept(TicketReceiptRequestDTO ticketReceiptRequestDTO) throws IOException {
        Ticket ticket = ticketRepository.findById(ticketReceiptRequestDTO.getId())
                .orElseThrow(() -> new TicketNotFoundException(ticketReceiptRequestDTO.getId()));
        ticket.setReceipt(ImageCompressor.compressZLib(ticketReceiptRequestDTO.getReceipt().getBytes()));
        ticket = ticketRepository.save(ticket);
        ticket.setReceipt(ImageCompressor.decompressZLib(ticket.getReceipt()));
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "En espera a que sea verificado su recibo",
                ticket);
    }

    /**
     * It gets all the tickets from the database, then it iterates over them and
     * sets the receipt to
     * the decompressed version of the receipt
     * 
     * @return A list of tickets with the receipt decompressed.
     */
    @Override
    public ResponseEntity<ApiResponse> findAll() {
        List<Ticket> tickets = ticketRepository.findAll().stream().peek(
                ticket -> ticket.setReceipt(
                        ticket.getReceipt() != null ? ImageCompressor.decompressZLib(ticket.getReceipt()) : null))
                .toList();
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Listado de todos los tickets", tickets);
    }

    /**
     * It takes a ticket id, checks if it's status is PENDIENTE, if it is, it
     * generates a QR code and
     * sets the status to REVISADO
     * 
     * @param id the id of the ticket
     * @return A QR code in the form of a byte array.
     */
    @Override
    public ResponseEntity<ApiResponse> verifyReceipt(String id) throws IOException, WriterException {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new TicketNotFoundException(id));
        if (ticket.getStatus().equals(TicketStatus.PENDIENTE)) {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(ticket.getId(), BarcodeFormat.QR_CODE, 200, 200);
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] qr = pngOutputStream.toByteArray();
            ticket.setQr(qr);
            ticket.setStatus(TicketStatus.REVISADO);
            ticket = ticketRepository.save(ticket);
            return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Código QR generado", ticket);
        }
        return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(),
                "El ticket esta caducado o ya esta verificado");
    }

    /**
     * It gets all the tickets from the database, then it iterates over them and
     * sets the receipt to
     * the decompressed version of the receipt
     * 
     * @param idClient String
     * @return A list of tickets
     */
    @Override
    public ResponseEntity<ApiResponse> findByClient(String idClient) {
        Client client = clientRepository.findById(idClient).orElseThrow(()-> new ClientNotFoundException(idClient));
        List<Ticket> tickets = ticketRepository.findByClient(client).stream().peek(
                ticket -> ticket.setReceipt(
                        ticket.getReceipt() != null ? ImageCompressor.decompressZLib(ticket.getReceipt()) : null))
                .toList();
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Listado de todos los tickets del cliente",
                tickets);
    }

    /**
     * The function checks if the ticket has been checked, if it has been checked it
     * returns a
     * response, if it hasn't been checked it checks if the ticket is valid and if
     * it is valid it sets
     * the ticket as checked and returns a response, if it isn't valid it returns a
     * response
     * 
     * @param id the id of the ticket
     * @return A ResponseEntity with a ApiResponse object.
     */
    @Override
    public ResponseEntity<ApiResponse> checkTicket(String id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new TicketNotFoundException(id));
        if (ticket.getCheck()) {
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "El ticket ya se ha verificado");
        }
        if (ticket.getStatus().equals(TicketStatus.REVISADO) && ticket.getQr() != null && !ticket.getCheck()) {
            ticket.setCheck(true);
            ticketRepository.save(ticket);
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Ticket verificado, puede ingresar al bus");
        } else if (ticket.getStatus().equals(TicketStatus.PENDIENTE)) {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "El ticket aun no ha sido verificado");
        } else {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(),
                    "El ticket esta caducado o no es válido");
        }
    }

}
