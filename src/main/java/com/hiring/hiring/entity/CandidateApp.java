package com.hiring.hiring.entity;

import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;



@Document(collection="CandidateApp")
@Getter
@Setter
@AllArgsConstructor
public class CandidateApp {

    @Id
    @Field(targetType = FieldType.OBJECT_ID,value = "idCandidateApp")
    private String idCandidateApp;

    @Field(value = "applicationDate")
    private Date applicationDate;

    @Field(value = "user")
    private User user;

    @Field(value = "Job")
    private JobOffers job;

    @Field(value = "cv")
    private String cv;

    @Field(value = "idTest")
    private List<String> idTest;

    @Field(value = "candidateState")
    private List<State> candidateState;


    public CandidateApp() {}



}

