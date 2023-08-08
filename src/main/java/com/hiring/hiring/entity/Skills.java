package com.hiring.hiring.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection="Skills")
@Getter
@Setter
@AllArgsConstructor
public class Skills {
    @Id
    private String id;
    private String iduser;
    @Enumerated(EnumType.STRING)
    private Type type;
    private List<Macroskills> macroskills;

    public Skills() {
        super();
    }




}