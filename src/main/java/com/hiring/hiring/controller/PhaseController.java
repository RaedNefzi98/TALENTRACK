package com.hiring.hiring.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;




@CrossOrigin(origins ="http://localhost:4200/")
@RequestMapping(value = "/api/Phases")
@RestController

public class PhaseController {
    @Autowired
    private IServicePhase iPhase;

    @Autowired
    private ModelMapper mapper;


    @PostMapping("/{idSection}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PhaseDTO> createPhase(@RequestParam("user") String phaseDto , @PathVariable("idSection") long idSection) throws IOException{
        Phase phase= new ObjectMapper().readValue(phaseDto, Phase.class);
        PhaseDTO responsePhase = mapper.map(phase, PhaseDTO.class);
        responsePhase =iPhase.addPhase(responsePhase,idSection);
        return new ResponseEntity<>(responsePhase, HttpStatus.OK);

    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PhaseDTO> updatePhase(@RequestParam("user") String postDto ,@PathVariable("id") long id) throws IOException{
        Phase post= new ObjectMapper().readValue(postDto, Phase.class);
        PhaseDTO responsePhase = mapper.map(post, PhaseDTO.class);
        responsePhase=iPhase.updatePhase(id, responsePhase);
        return new ResponseEntity<>(responsePhase, HttpStatus.OK);

    }
    @PutMapping("/updateName/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PhaseDTO> updatePhaseName(@PathVariable("id") long id,@RequestBody PhaseDTO sectionDto) {

        PhaseDTO responsePhase=iPhase.updatePhase(id, sectionDto);
        return new ResponseEntity<>(responsePhase, HttpStatus.OK);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PhaseDTO> getAllPhases(){

        return  iPhase.getList();

    }

    @GetMapping("section/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<PhaseDTO> getPhaseByFormation(@PathVariable("id") long id){
        List<PhaseDTO> postDto1=new ArrayList<PhaseDTO>();
        return iPhase.getPhaseByIdSection(id);


    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PhaseDTO getPhaseById(@PathVariable(name = "id") Long id){
        return iPhase.getPhaseById(id);
    }





    @DeleteMapping(path = "/{idPhase}")
    public void deleteCourse(@PathVariable(name = "idPhase") Long idPhase) {
        iPhase.deletePhase(idPhase);

    }
}

