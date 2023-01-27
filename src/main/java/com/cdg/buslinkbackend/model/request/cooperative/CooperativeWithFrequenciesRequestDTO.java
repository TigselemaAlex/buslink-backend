package com.cdg.buslinkbackend.model.request.cooperative;

import lombok.*;

import java.util.List;

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
