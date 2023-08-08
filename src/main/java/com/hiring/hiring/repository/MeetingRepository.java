package com.hiring.hiring.repository;


import org.springframework.stereotype.Repository;


@Repository
public interface MeetingRepository extends MongoRepository<Meeting, Integer> {
    public List<Meeting> findAllByIdFormation(long idFormation);
    public List<Meeting> findAllByIdInstructor(long idInstructor);


}

