package com.cdg.buslinkbackend.model.request.cooperative;

import lombok.*;

import java.util.List;

/**
 * This class is a DTO that contains a cooperative_id and a list of
 * frequencies_ids.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CooperativeWithFrequenciesRequestDTO {

    private String cooperative_id;

    private List<String> frequencies_ids;

}
