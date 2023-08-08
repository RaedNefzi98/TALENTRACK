package com.hiring.hiring.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;



@Document(collection = "Meeting")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Meeting implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Transient
    public static final String SEQUENCE_NAME = "database_sequences";

    @Id
    @Field(value = "id")
    private String id;
    @Field(value = "formation")
    private long idFormation;
    @Field(value = "instructor")
    private long idInstructor;
    @Field(value = "date")
    private Date date;
    @Field(value = "link")
    private String link;

}

