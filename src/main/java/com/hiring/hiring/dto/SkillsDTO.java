package com.hiring.hiring.dto;



@Getter
@Setter
@AllArgsConstructor
public class SkillsDTO {
    private String id;
    private String iduser;
    @Enumerated(EnumType.STRING)
    private Type type;
    private List<MacroskillsDTO> macroskills;

    public SkillsDTO()
    {
        super();
    }
}
