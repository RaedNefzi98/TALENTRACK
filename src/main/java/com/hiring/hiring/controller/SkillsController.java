package com.hiring.hiring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping( "/skills" )
@RestController
public class SkillsController {
    @Autowired
    private IServiceSkills iskills;
    @Autowired
    SkillsRepository skillsrepository;

    @GetMapping("/get_skillsbyid/{id}")
    public SkillsDTO showskillbyid(@PathVariable("id") String id) {

        return  iskills.getskillbyid(id);

    }



    //get macro hard skills from one user
    @GetMapping(path = "/getHardMacroByIdUser/{id}")
    public List<MacroskillsDTO> gethardmacroskills(@PathVariable("id") String id) {

        return iskills.getmacroskills(id, Type.HARD);
    }

    //get macro soft skills from one user
    @GetMapping(path = "/getSoftMacroByIdUser/{id}")
    public List<MacroskillsDTO> getSoftmacroskills(@PathVariable("id") String id) {
        return iskills.getmacroskills(id,Type.SOFT);
    }

    //get micro skills from one user according to a hard skill
    @GetMapping(path = "/getHardMicroByIdUser/{id}/{name}")
    public List<MicroskillsDTO> gethardmicrobyiduser(@PathVariable("id") String id , @PathVariable("name")  String name) {
        return iskills.getmicroskills(id,name,Type.HARD);
    }
    //get micro skills from one user according to a hard skill
    @GetMapping(path = "/getSoftMicroByIdUser/{id}/{name}")
    public List<MicroskillsDTO> getsoftmicrobyiduser(@PathVariable("id") String id , @PathVariable("name")  String name) {
        return iskills.getmicroskills(id,name,Type.SOFT);
    }
    //get all micro skills from one user according to a hard skill
    @GetMapping(path = "/getallmicrosoft/{id}")
    public List<List<Microskills>> allmicro(@PathVariable("id") String id) {
        return iskills.getallmicroskills("123",Type.SOFT);
    }
}


