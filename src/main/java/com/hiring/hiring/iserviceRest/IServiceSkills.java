package com.hiring.hiring.iserviceRest;



public interface IServiceSkills {

    public SkillsDTO getskillbyid(String id);
    public List<MacroskillsDTO> getmacroskills(String id, Type type);
    public List<MicroskillsDTO> getmicroskills(String iduser, String name, Type type);
    public List<List<Microskills>> getallmicroskills(String iduser, Type type);
}
