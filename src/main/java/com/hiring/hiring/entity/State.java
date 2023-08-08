package com.hiring.hiring.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Transient;


@Getter
@Setter
@AllArgsConstructor
public class State {

    @Transient
    public static final String SEQUENCE_NAME = "applications_sequence";
    private long idCandidateState;
    private Date stateDate;
    private String label;
    private int step;
    private String idPrehiringTest;
    private String idTest;
    private String linkMeet;
    private Date interviewDate;
    private int score;
    private boolean testState;
    private String offerDoc;
    public State() {}
}
