package com.hiring.hiring.serviceRest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class InscriptionBusinesses implements IServiceInscription {
    @Autowired
    InscriptionRepository inscriptionRepository;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private  SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    private FormationBusinesses formationService;
    @Autowired
    FormationRepository formationRepository;
    @Override
    public InscriptionDTO addInscription(InscriptionDTO f, Long idEtudiant, Long idFormation) {
        Inscription inscription = mapper.map(f,Inscription.class);
        inscription.setId(Long.valueOf(sequenceGeneratorService.generateSequence(Formation.SEQUENCE_NAME)));
        inscription.setIdEtudiant(idEtudiant);
        inscription.setIdFormation(idFormation);
        Inscription newInscri =inscriptionRepository.save(inscription);

        return mapper.map(newInscri,InscriptionDTO.class);
    }

    @Override
    public List<User> getListInscription(Long id) {
        List<User> users=new  ArrayList<User>();
        List<Inscription> inscriptions=inscriptionRepository.findByIdFormation(id);
        for(Inscription i:inscriptions) {
            User user=formationService.getUserByRestTemplate(i.getIdEtudiant());
            System.out.println("user" +user.getDisplayName() );
            users.add(user);
        }
        return users;
    }



    @Override
    public List<FormationDTO> getAllFormationsByUser(Long idUser) {

        List<FormationDTO> formationDtoList = new ArrayList<FormationDTO>();
        List<Inscription> inscriptions=inscriptionRepository.findAllByIdEtudiant(idUser);
        inscriptions.stream().forEach(elem ->formationDtoList.add(mapper.map(formationRepository.findByIdFormation(elem.getIdFormation()), FormationDTO.class)) );


        return formationDtoList;
    }

    @Override
    public boolean checkFormationByUser(long formationId, Long idUser) {


        return inscriptionRepository.existsByIdFormationAndIdEtudiant(formationId, idUser);

    }










}

