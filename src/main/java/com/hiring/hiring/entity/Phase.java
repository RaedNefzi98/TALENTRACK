package com.hiring.hiring.entity;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;



@Document(collection="Phase")

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Phase {


    @Transient
    public static final String SEQUENCE_NAME = "database_sequences";

    @Id
    @Field(value = "IdPhase")
    private long idPhase;

    @Field(value = "PostContent")
    private String postContent;

    @Field(value = "Title")
    private String title;

    @Field(value = "Date")
    private Date date;

    @Field(value = "IdSection")
    private long idsection;




    @Field(value = "typefile")
    private String typefile;









}

