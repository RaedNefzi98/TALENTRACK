package com.hiring.hiring.serviceRest;

import java.util.Date;

import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;





@Service
public class PhaseBusinesses implements IServicePhase {

    @Autowired
    PhaseRepository phaseRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private  SequenceGeneratorService sequenceGeneratorService;

    @Override
    public List<PhaseDTO> getList() {
        List<Phase> phases = phaseRepository.findAll();
        return phases.stream().map(phase -> mapper.map(phase, PhaseDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public PhaseDTO getPhaseById(long idPhase) {
        Phase phase=phaseRepository.findByIdPhase(idPhase);
        return  mapper.map(phase, PhaseDTO.class);


    }
    @Override
    public PhaseDTO updatePhase(long idPhase, PhaseDTO requestPhase) {
        Phase phase = mapper.map(requestPhase, Phase.class);
        Phase phase2=phaseRepository.findByIdPhase(idPhase);

        phase.setIdPhase(idPhase);
        phase.setIdsection(phase2.getIdsection());

        phase.setDate(phase2.getDate());
        Phase newPhase = phaseRepository.save(phase);
        return  mapper.map(newPhase, PhaseDTO.class);


    }
    @Override
    public void deletePhase(long idPhase) {
        Phase phase=phaseRepository.findByIdPhase(idPhase);
        phaseRepository.delete(phase);


    }


    @Override
    public List<PhaseDTO> getPhaseByIdSection(long id) {
        List<Phase> phases =phaseRepository.findByIdsection(id);
        return phases.stream().map(phase -> mapper.map(phase, PhaseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PhaseDTO addPhase(PhaseDTO f, long idSection) {
        Phase phase = mapper.map(f, Phase.class);
        phase.setIdPhase(Long.parseLong(sequenceGeneratorService.generateSequence(Phase.SEQUENCE_NAME)));
        phase.setIdsection(idSection);
        phase.setDate(new Date());
        Phase newPhase=phaseRepository.save(phase);
        return mapper.map(newPhase, PhaseDTO.class);

    }





}

