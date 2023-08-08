package com.hiring.hiring.repository;

import java.util.List;


import org.springframework.stereotype.Repository;





@Repository
public interface CandidateAppRepository extends MongoRepository<CandidateApp,String> {

    //this repository contain all operations of mongodb
    public CandidateApp findByIdCandidateApp(String idCandidateApp);
    public List<CandidateApp> findByJobBusinessIdBusiness(String idBusiness);
    public boolean existsByJobIdJobAndUserId(String idJob,Long userId);

    public List<CandidateApp> findByJobIdJob(String idJob);
    public List<CandidateApp> findByUserId(long id);

}