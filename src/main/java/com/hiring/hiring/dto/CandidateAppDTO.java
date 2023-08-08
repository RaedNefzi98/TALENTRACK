package com.hiring.hiring.dto;

import java.util.Date;
import java.util.List;




@Getter
@Setter
@AllArgsConstructor
public class CandidateAppDTO {

    private String idCandidateApp;
    private Date applicationDate;
    private User user;
    private JobOffers job;
    private String cv;
    private List<String> idTest;
    private List<StateDTO> candidateState;
    public CandidateAppDTO() {}




}
