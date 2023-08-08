package com.hiring.hiring.iserviceRest;




public interface IServicePhase {
    public PhaseDTO addPhase(PhaseDTO f, long idSection);

    public List<PhaseDTO>getList();
    public List<PhaseDTO>getPhaseByIdSection(long id);
    public PhaseDTO getPhaseById(long idPhase);
    public PhaseDTO updatePhase(long idPhase ,PhaseDTO requestPhase);

    public void deletePhase(long idPhase);

}
