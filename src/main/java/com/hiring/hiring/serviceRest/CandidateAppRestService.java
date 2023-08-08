package com.hiring.hiring.serviceRest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;










@Service
public class CandidateAppRestService implements IServiceRestCandidateApp {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private  CandidateAppRepository candidateAppRepository;

    @Autowired
    private  SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    public JavaMailSender mailSender;

    @Autowired
    private FileService fileService;


    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    private static final String GET_USER_BY_ID_API = "https://auth-herokuu.herokuapp.com/api/auth/getUser/{id}";
    private static final String GET_JOB_BY_ID_API = "https://joboffers-application.herokuapp.com/JobOffers/{idJob}";


    public List<CandidateAppDTO> getAllapplications(){
        List<CandidateApp> apps = candidateAppRepository.findAll();

        apps.forEach(app->{
            if(app.getUser()!=null) {
                Map<String, Long> params = new HashMap<String, Long>();
                params.put("id", Long.parseLong(app.getUser().getId()));
                User user = restTemplateBuilder.build().getForObject(GET_USER_BY_ID_API, User.class, params);
                app.setUser(user);
            }

            if(app.getJob()!=null) {
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("idJob", app.getJob().getIdJob());
                JobOffers job = restTemplateBuilder.build().getForObject(GET_JOB_BY_ID_API, JobOffers.class, params2);
                app.setJob(job);
            }
        });

        return apps.stream().map(app -> mapper.map(app, CandidateAppDTO.class))
                .collect(Collectors.toList());


    }

    public List<CandidateAppDTO> getAppByBusiness(String idBusiness){
        List<CandidateApp> apps = candidateAppRepository.findByJobBusinessIdBusiness(idBusiness);

        apps.forEach(app->{
            if(app.getUser()!=null) {
                Map<String, Long> params = new HashMap<String, Long>();
                params.put("id", Long.parseLong(app.getUser().getId()));
                User user = restTemplateBuilder.build().getForObject(GET_USER_BY_ID_API, User.class, params);
                app.setUser(user);
            }

            if(app.getJob()!=null) {
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("idJob", app.getJob().getIdJob());
                JobOffers job = restTemplateBuilder.build().getForObject(GET_JOB_BY_ID_API, JobOffers.class, params2);
                app.setJob(job);
            }
        });

        return apps.stream().map(app -> mapper.map(app, CandidateAppDTO.class))
                .collect(Collectors.toList());


    }




    public CandidateAppDTO getCandidateById(String idCandidateApp ) {

        CandidateApp app = candidateAppRepository.findByIdCandidateApp(idCandidateApp);

        Map<String, Long> params = new HashMap<String, Long>();
        params.put("id", Long.parseLong(app.getUser().getId()));
        User user = restTemplateBuilder.build().getForObject(GET_USER_BY_ID_API , User.class, params);
        app.setUser(user);


        Map<String, String> params2 = new HashMap<String, String>();
        params2.put("idJob", app.getJob().getIdJob());
        JobOffers job = restTemplateBuilder.build().getForObject(GET_JOB_BY_ID_API, JobOffers.class, params2);
        app.setJob(job);


        return mapper.map(app, CandidateAppDTO.class);

    }



    public List<CandidateAppDTO> getCandidateByUser(Long userId) {

        List<CandidateApp> apps = candidateAppRepository.findByUserId(userId);

        apps.forEach(app->{
            if(app.getUser()!=null) {
                Map<String, Long> params = new HashMap<String, Long>();
                params.put("id", Long.parseLong(app.getUser().getId()));
                User user = restTemplateBuilder.build().getForObject(GET_USER_BY_ID_API , User.class, params);
                app.setUser(user);
            }

            if(app.getJob()!=null) {
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("idJob", app.getJob().getIdJob());
                JobOffers job = restTemplateBuilder.build().getForObject(GET_JOB_BY_ID_API, JobOffers.class, params2);
                app.setJob(job);
            }
        });

        return apps.stream().map(app -> mapper.map(app, CandidateAppDTO.class))
                .collect(Collectors.toList());

    }

    public List<CandidateAppDTO> getCandidateByJob(String idJob) {

        List<CandidateApp> apps = candidateAppRepository.findByJobIdJob(idJob);

        apps.forEach(app->{
            if(app.getUser()!=null) {
                Map<String, Long> params = new HashMap<String, Long>();
                params.put("id", Long.parseLong(app.getUser().getId()));
                User user = restTemplateBuilder.build().getForObject(GET_USER_BY_ID_API , User.class, params);
                app.setUser(user);
            }

            if(app.getJob()!=null) {
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("idJob", app.getJob().getIdJob());
                JobOffers job = restTemplateBuilder.build().getForObject(GET_JOB_BY_ID_API, JobOffers.class, params2);
                app.setJob(job);
            }
        });


        return apps.stream().map(app -> mapper.map(app, CandidateAppDTO.class))
                .collect(Collectors.toList());

    }


    public StateDTO getCurrentState(String idCandidateApp) {
        CandidateApp app = candidateAppRepository.findByIdCandidateApp(idCandidateApp);

        State state = app.getCandidateState().get(app.getCandidateState().size()-1);

        return  mapper.map(state, StateDTO.class);

    }

    public boolean ExistApp(String idJob,Long id) {
        return candidateAppRepository.existsByJobIdJobAndUserId(idJob,id);
    }


    public CandidateAppDTO addApp(CandidateAppDTO candidateApp,String idJob,Long id) throws IOException {
        if(candidateAppRepository.existsByJobIdJobAndUserId(idJob,id)) {
            throw new IOException("Application for this job already exist");
        }
        else {
            CandidateApp app=mapper.map(candidateApp, CandidateApp.class);


            Map<String, Long> params = new HashMap<String, Long>();
            params.put("id", id);
            User user = restTemplateBuilder.build().getForObject(GET_USER_BY_ID_API , User.class, params);
            app.setUser(user);


            Map<String, String> params2 = new HashMap<String, String>();
            params2.put("idJob", idJob);
            JobOffers job = restTemplateBuilder.build().getForObject(GET_JOB_BY_ID_API, JobOffers.class, params2);
            app.setJob(job);

            CandidateApp newApp = candidateAppRepository.save(app);
            return mapper.map(newApp, CandidateAppDTO.class);
        }

    }

    public CandidateAppDTO updateApp(String idCandidateApp,CandidateAppDTO candidateApp) {

        CandidateApp app = mapper.map(candidateApp, CandidateApp.class);

        CandidateApp theApp = candidateAppRepository.findByIdCandidateApp(idCandidateApp);
        theApp.setIdCandidateApp(app.getIdCandidateApp());
        theApp.setApplicationDate(app.getApplicationDate());
        theApp.setCandidateState(app.getCandidateState());
        theApp.setIdTest(app.getIdTest());

        Map<String, Long> params = new HashMap<String, Long>();
        params.put("id", Long.parseLong(app.getUser().getId()));
        User user = restTemplateBuilder.build().getForObject(GET_USER_BY_ID_API, User.class, params);
        theApp.setUser(user);


        Map<String, String> params2 = new HashMap<String, String>();
        params2.put("idJob", app.getJob().getIdJob());
        JobOffers job = restTemplateBuilder.build().getForObject(GET_JOB_BY_ID_API, JobOffers.class, params2);
        theApp.setJob(job);

        CandidateApp newApp = candidateAppRepository.save(theApp);



        return mapper.map(newApp,CandidateAppDTO.class);

    }


    public CandidateAppDTO addState(String idCandidateApp,StateDTO candidateState) {


        State state = mapper.map(candidateState, State.class);

        state.setIdCandidateState(Long.parseLong(sequenceGeneratorService.generateSequence(State.SEQUENCE_NAME)));

        Query query = new Query();

        query.addCriteria(Criteria.where("idCandidateApp").is(idCandidateApp));

        Update update = new Update();

        update.addToSet("candidateState", state );

        mongoTemplate.findAndModify(query, update, FindAndModifyOptions.options().upsert(true), CandidateApp.class);
        CandidateApp app  = candidateAppRepository.findByIdCandidateApp(idCandidateApp);


        Map<String, Long> params = new HashMap<String, Long>();
        params.put("id",Long.parseLong( app.getUser().getId()));
        User user = restTemplateBuilder.build().getForObject(GET_USER_BY_ID_API , User.class, params);
        app.setUser(user);


        Map<String, String> params2 = new HashMap<String, String>();
        params2.put("idJob", app.getJob().getIdJob());
        JobOffers job = restTemplateBuilder.build().getForObject(GET_JOB_BY_ID_API, JobOffers.class, params2);
        app.setJob(job);

        app=candidateAppRepository.save(app);
        return mapper.map(app, CandidateAppDTO.class);
    }




    public void deleteApp(String idCandidateApp) {
        candidateAppRepository.deleteById(idCandidateApp);

    }

    public void sendMail(String email,String content) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("haithemelmetoui313@gmail.com", "Courzelo for business Support");
        helper.setTo(email);

        String subject = "Hello , ";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);

    }



    public CandidateAppDTO addCv(String idCandidateApp,MultipartFile cv) throws IOException {
        CandidateApp theApp = candidateAppRepository.findByIdCandidateApp(idCandidateApp);
        theApp.setCv(fileService.addFile(cv));

        Map<String, Long> params = new HashMap<String, Long>();
        params.put("id", Long.parseLong(theApp.getUser().getId()));
        User user = restTemplateBuilder.build().getForObject(GET_USER_BY_ID_API , User.class, params);
        theApp.setUser(user);


        Map<String, String> params2 = new HashMap<String, String>();
        params2.put("idJob", theApp.getJob().getIdJob());
        JobOffers job = restTemplateBuilder.build().getForObject(GET_JOB_BY_ID_API, JobOffers.class, params2);
        theApp.setJob(job);


        CandidateApp newApp = candidateAppRepository.save(theApp);
        return mapper.map(newApp, CandidateAppDTO.class);


    }


    public CandidateAppDTO saveOffer(String idCandidateApp,long idCandidateState,MultipartFile offerDoc) throws IOException {

        CandidateApp theApp = candidateAppRepository.findByIdCandidateApp(idCandidateApp);
        theApp.getCandidateState().forEach(s->{
            if(s.getIdCandidateState()==idCandidateState) {
                try {
                    s.setOfferDoc(fileService.addFile(offerDoc));


                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        });


        Map<String, Long> params = new HashMap<String, Long>();
        params.put("id", Long.parseLong(theApp.getUser().getId()));
        User user = restTemplateBuilder.build().getForObject(GET_USER_BY_ID_API , User.class, params);
        theApp.setUser(user);


        Map<String, String> params2 = new HashMap<String, String>();
        params2.put("idJob", theApp.getJob().getIdJob());
        JobOffers job = restTemplateBuilder.build().getForObject(GET_JOB_BY_ID_API, JobOffers.class, params2);
        theApp.setJob(job);


        CandidateApp newApp = candidateAppRepository.save(theApp);
        return mapper.map(newApp, CandidateAppDTO.class);


    }


    public LoadFile getPdf(String pdf) throws IOException {
        return fileService.downloadFile(pdf);



    }









}
