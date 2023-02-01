package com.cdg.buslinkbackend.util.compressor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * It takes a byte array, compresses it using the ZLib algorithm, and returns
 * the compressed byte array
 */
public class ImageCompressor {

    /**
     * Deflate the input byte array and return the deflated byte array.
     * 
     * @param image The image to compress
     * @return A byte array.
     */
    public static byte[] compressZLib(byte[] image) {
        Deflater deflater = new Deflater();
        deflater.setInput(image);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(image.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outputStream.toByteArray();
    }

    /**
     * It takes a byte array, decompresses it using the ZLib algorithm, and returns
     * the decompressed
     * byte array
     * 
     * @param image The image to be decompressed
     * @return A byte array.
     */
    public static byte[] decompressZLib(byte[] image) {
        Inflater inflater = new Inflater();
        inflater.setInput(image);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(image.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException e) {
            throw new RuntimeException(e);
        }
        return outputStream.toByteArray();
    }
}
