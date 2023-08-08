package com.hiring.hiring.serviceRest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;





@Service
public class FormationBusinesses implements IServiceFormation {

    @Autowired
    FormationRepository formationRepository;
    @Autowired
    InscriptionRepository inscriptionRepository ;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private  SequenceGeneratorService sequenceGeneratorService;

    @Autowired

    IServiceComment iServiceComment;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    PostRepository postRepository;
    private static final String GET_USER_BY_ID_API = "http://localhost:8082/api/auth/getUser/{id}";


    @Override
    public List<FormationDTO> getList() {
        List<Formation> formations= formationRepository.findAll();
        return formations.stream().map(formation -> mapper.map(formation, FormationDTO.class))
                .collect(Collectors.toList());
    }



    public User getUserByRestTemplate(long id){
        Map<String, Long> param = new HashMap<>();
        param.put("id", id);
        return restTemplateBuilder.build().getForObject(GET_USER_BY_ID_API, User.class, param);

    }


    @Override
    public FormationDTO addformation(FormationDTO f, Long idUserr) {
        Formation formation = mapper.map(f, Formation.class);
        formation.setIdFormation(Long.parseLong(sequenceGeneratorService.generateSequence(Formation.SEQUENCE_NAME)));
        formation.setTest(true);
        formation.setIdUserr(idUserr);
        Formation newFormation = formationRepository.save(formation);

        return  mapper.map(newFormation, FormationDTO.class);

    }



    @Override
    public FormationDTO getFormationById(long idFormation) {
        Formation formation=formationRepository.findByIdFormation(idFormation);
        return mapper.map(formation, FormationDTO.class);
    }



    @Override
    public FormationDTO updateFormation(long idFormation, FormationDTO requestFormation) {
        Formation formation = mapper.map(requestFormation, Formation.class);


        formation.setIdFormation(idFormation);
        Formation newFormation = formationRepository.save(formation);
        return mapper.map(newFormation, FormationDTO.class);




    }



    @Override
    public void deleteFormation(long idFormation) {
        Formation formation = formationRepository.findByIdFormation(idFormation);
        formation.setTest(false);
        formation.setIdFormation(idFormation);
        formationRepository.save(formation);


    }



    @Override
    public List<Formation> getFormationByIdStudent(Long id) {
        List<Formation> formations=new ArrayList<Formation>();
        List<Inscription> inscriptions= inscriptionRepository.findByIdEtudiant(id);
        System.out.println("resultat "+ formations+ inscriptions);
        for(Inscription i:inscriptions) {
            Formation f=formationRepository.findByIdFormation(i.getIdFormation());
            formations.add(f);
            System.out.println("resultat "+ formations+ inscriptions);

        }

        return formations;

    }



    @Override
    public List<FormationDTO> getFormtionByIdCreator(Long id) {
        List<Formation> formations=formationRepository.findByIdUserr(id);
        return formations.stream().map(formation -> mapper.map(formation, FormationDTO.class))
                .collect(Collectors.toList());
    }



    @Override
    public List<FormationDTO> getFormtionByTest(Boolean test,Long id) {

        List<Formation> formations= formationRepository.findByTestAndIdUserr(test, id);
        return formations.stream().map(formation -> mapper.map(formation, FormationDTO.class))
                .collect(Collectors.toList());
    }

    public FormationDTO addQuiz(long id,String idQuiz) {

        Formation formation = formationRepository.findByIdFormation(id);
        if(formation.getIdQuiz()==null) {
            formation.setIdQuiz(new ArrayList<String>());
        }

        List<String> ids =formation.getIdQuiz();
        ids.add(idQuiz);
        formation.setIdQuiz(ids);
        Formation f = formationRepository.save(formation);
        return mapper.map(f, FormationDTO.class);

    }

    public List<InscriptionappDTO> getStats(Long id)
    {
        List<FormationDTO> formations=  this.getFormtionByIdCreator( id);
        List<InscriptionappDTO> result = new ArrayList<InscriptionappDTO>();

        for(FormationDTO item:formations )
        {
            InscriptionappDTO inscriptionappDTO =  new InscriptionappDTO();
            inscriptionappDTO.setIdFormation(item.getIdFormation());
            inscriptionappDTO.setNameFormation(item.getCoursename());
            inscriptionappDTO.setNbrEtudiants(inscriptionRepository.countByIdFormation(item.getIdFormation()));
            result.add(inscriptionappDTO);
        }
        return result;
    }



    @Override
    public List<FormationTeacherDTO> getFormtionByIdInstructor(Long id) {
        List<Formation> formations=formationRepository.findByIdUserr(id);
        return formations.stream().map(formation -> mapper.map(formation, FormationTeacherDTO.class))
                .collect(Collectors.toList());
    }



    @Override
    public List<FormationStudentsStatsDto> getStatsFormationStudents(Long idFormation) {
        List<FormationStudentsStatsDto> result = new ArrayList<FormationStudentsStatsDto>();
        List<User> users=new  ArrayList<User>();
        List<Inscription> inscriptions=inscriptionRepository.findByIdFormation(idFormation);
        for(Inscription i:inscriptions) {
            User user=this.getUserByRestTemplate(i.getIdEtudiant());
            users.add(user);
        }

        users.forEach(
                elem -> {
                    FormationStudentsStatsDto formationStudentsStatsDto = new FormationStudentsStatsDto();
                    formationStudentsStatsDto.setStudentName(elem.getDisplayName());
                    formationStudentsStatsDto.setNbrPosts(postRepository.countByIduserAndIdformation(Long.parseLong(elem.getId()), idFormation));
                    formationStudentsStatsDto.setNbrComments(iServiceComment.countCommentsByUserIdAndFormationId(Long.parseLong(elem.getId()), idFormation));
                    result.add(formationStudentsStatsDto);
                }
        );
        return result;
    }
}

