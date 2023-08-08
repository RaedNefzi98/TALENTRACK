package com.hiring.hiring.serviceRest;


import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class MeetingBusiness implements IserviceMeeting {
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public Meeting addMeeting(Meeting m) {
        return meetingRepository.save(m);
    }

    @Override
    public List<MeetingDTO> getMeetingsByFormation(long idFormation) {


        List<Meeting> meetingList = meetingRepository.findAllByIdFormation(idFormation);

        return meetingList.stream().map(elem -> mapper.map(elem, MeetingDTO.class)).collect(Collectors.toList());

    }

    @Override
    public List<MeetingDTO> getMeetingsByUser(long idUser) {


        List<Meeting> meetingList = meetingRepository.findAllByIdInstructor(idUser);

        return meetingList.stream().map(elem -> mapper.map(elem, MeetingDTO.class)).collect(Collectors.toList());

    }
}

