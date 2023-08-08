package com.hiring.hiring.repository;


import org.springframework.stereotype.Repository;


@Repository
public interface PhaseRepository extends MongoRepository<Phase, Integer> {

    public List<Phase> findByIdsection(long idSection);
    public Phase findByIdPhase(long idPhase);

}

