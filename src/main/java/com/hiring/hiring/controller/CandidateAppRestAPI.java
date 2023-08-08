package com.hiring.hiring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;







@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping( "/CandidateApp" )
@RestController
public class CandidateAppRestAPI {

    @Autowired
    private IServiceRestCandidateApp iCandidateApp;

    @GetMapping(path = "")
    public List<CandidateAppDTO> getAllApp() {
        return iCandidateApp.getAllapplications();
    }



    @GetMapping(path = "/{idCandidateApp}")
    public CandidateAppDTO getCandidateAppById(@PathVariable(name = "idCandidateApp") String idCandidateApp) {
        return iCandidateApp.getCandidateById(idCandidateApp);
    }

    @GetMapping(path = "business/{idBusiness}")
    public List<CandidateAppDTO> getCandidateAppByBusiness(@PathVariable(name = "idBusiness") String idBusiness) {
        return iCandidateApp.getAppByBusiness(idBusiness);
    }

    @GetMapping(path = "/byJob/{idJob}")
    public List<CandidateAppDTO> getCandidateAppByIdJob(@PathVariable(name = "idJob") String idJob) {
        return iCandidateApp.getCandidateByJob(idJob);
    }

    @GetMapping(path = "/byUser/{userId}")
    public List<CandidateAppDTO> getCandidateAppByUser(@PathVariable(name = "userId") Long userId) {
        return iCandidateApp.getCandidateByUser(userId);
    }

    @GetMapping(path = "/currentState/{idCandidateApp}")
    public StateDTO getCurrentState(@PathVariable(name = "idCandidateApp") String idCandidateApp) {
        return iCandidateApp.getCurrentState(idCandidateApp);
    }

    @GetMapping(path = "/Exist/{idJob}/{id}")
    public boolean ExistApp(@PathVariable(name = "idJob") String idJob,@PathVariable(name = "id") Long id) {
        return iCandidateApp.ExistApp(idJob,id);
    }


    @PostMapping(path ="/{idJob}/{id}")
    public ResponseEntity<?> addApp(@RequestBody  @Valid  CandidateAppDTO app,@PathVariable(name = "idJob") String idJob,@PathVariable(name = "id") Long id) throws IOException {
        try {
            CandidateAppDTO appResponse = iCandidateApp.addApp(app,idJob,id);
            return new ResponseEntity<CandidateAppDTO>(appResponse, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>( e.getMessage(),HttpStatus.ACCEPTED);
        }


    }

    @PostMapping(path = "/State/{idCandidateApp}")
    public ResponseEntity<CandidateAppDTO> addState(@PathVariable(name = "idCandidateApp") String idCandidateApp,@RequestBody  @Valid  StateDTO state) {

        CandidateAppDTO appResponse = iCandidateApp.addState(idCandidateApp,state);

        return new ResponseEntity<CandidateAppDTO>(appResponse, HttpStatus.CREATED);
    }



    @PutMapping(path = "/{idCandidateApp}")
    public ResponseEntity<CandidateAppDTO> updateJob(@PathVariable(name = "idCandidateApp") String idCandidateApp, @RequestBody  @Valid  CandidateAppDTO app) {

        CandidateAppDTO appResponse = iCandidateApp.updateApp(idCandidateApp,app);

        return new ResponseEntity<CandidateAppDTO>(appResponse, HttpStatus.CREATED);
    }


    @PostMapping(path = "/mail/{email}")
    public void sendMail(@PathVariable(name = "email") String email,@RequestBody  @Valid  String content) throws UnsupportedEncodingException, MessagingException {

        iCandidateApp.sendMail(email,content);


    }





    @DeleteMapping(path = "/{idCandidateApp}")
    public void deleteJob(@PathVariable(name = "idCandidateApp") String idCandidateApp) {
        iCandidateApp.deleteApp(idCandidateApp);

    }


    @PutMapping("/uploadCv/{idCandidateApp}")
    public ResponseEntity<CandidateAppDTO> upload(@PathVariable(name = "idCandidateApp") String idCandidateApp,@RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<CandidateAppDTO>(iCandidateApp.addCv(idCandidateApp,file), HttpStatus.OK);
    }


    @PutMapping("/uploadOffer/{idCandidateApp}/{idCandidateState}")
    public ResponseEntity<CandidateAppDTO> uploadOffer(@PathVariable(name = "idCandidateApp") String idCandidateApp,@PathVariable(name = "idCandidateState") long idCandidateState,@RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<CandidateAppDTO>(iCandidateApp.saveOffer(idCandidateApp,idCandidateState,file), HttpStatus.OK);
    }


    @GetMapping("/downloadPdf/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
        LoadFile loadFile = iCandidateApp.getPdf(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(loadFile.getFileType() ))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + loadFile.getFilename() + "\"")
                .body(new ByteArrayResource(loadFile.getFile()));
    }



}

