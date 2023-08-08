package com.hiring.hiring.iserviceRest;




public interface IserviceMeeting {
    public Meeting addMeeting(Meeting m);

    public List<MeetingDTO> getMeetingsByFormation(long idFormation);

    public List<MeetingDTO> getMeetingsByUser(long idUser);

}
