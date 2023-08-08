package com.hiring.hiring.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseBusinessApiRest  implements IServiceCourse {

    @Autowired
    private CourseRepository reposCourse;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;
    private static final String GET_USER_BY_ID_API = "https://auth-herokuu.herokuapp.com/api/auth/getUser/{id}";


    public User getUserByRestTemplate(long id){
        Map<String, Long> param = new HashMap<>();
        param.put("id", id);
        return restTemplateBuilder.build().getForObject(GET_USER_BY_ID_API, User.class, param);
    }

    @Override
    public CourseDTO addCourse(CourseDTO courseDTO){
        System.out.println("Before mapping: courseDTO = " + courseDTO);
        Course course = mapper.map(courseDTO, Course.class);
        System.out.println("After mapping: course = " + course);
        //Course newcourse=reposCourse.save(course);
        Course newcourse = reposCourse.save(course);
        System.out.println("Saved course: {} " + newcourse);
        CourseDTO newCourseDTO = mapper.map(newcourse, CourseDTO.class);
        System.out.println("After mapping: course = " + newCourseDTO);
        return newCourseDTO;

        //return mapper.map(newcourse,CourseDTO.class);
    }
    @Override
    public List<CourseDTO> getAllCourses() {
        List<Course> lstCourse=reposCourse.findAll();
        return lstCourse.stream().map(e -> mapper.map(e, CourseDTO.class))
                .collect(Collectors.toList());

    }
    @Override
    public CourseDTO getCourseById(String  idCourse) {
        Course course = reposCourse.findById(idCourse).orElseGet(Course::new);
        return mapper.map(course,CourseDTO.class);
    }
    @Override
    public CourseDTO putCourseById(String  idCourse, CourseDTO courseDTO) {
        Course course = mapper.map(courseDTO, Course.class);
        Course thecourse = reposCourse.findById(idCourse).orElseGet(Course::new);
        if(courseDTO.getTitle()!= null)
            thecourse.setTitle(course.getTitle());

        if(courseDTO.getLanguage()!= null)
            thecourse.setLanguage(course.getLanguage());

        if(courseDTO.getCategory()!= null)
            thecourse.setCategory(course.getCategory());

        if(courseDTO.getOutcomes()!= null)
            thecourse.setOutcomes(course.getOutcomes());

        if(courseDTO.getDescription()!= null)
            thecourse.setDescription(course.getDescription());


        if(courseDTO.getDetails()!= null)
            thecourse.setDetails(course.getDetails());

        if(courseDTO.getSkillsRequired()!= null)
            thecourse.setSkillsRequired(course.getSkillsRequired());

        //introduction part


        thecourse.setIntroduction(course.getIntroduction());

        //section part
        if(courseDTO.getSections()!= null)
            thecourse.setSections(course.getSections());

        if(courseDTO.getCustomerSections()!= null)
            thecourse.setCustomerSections(course.getCustomerSections());
        //conclusion part

        thecourse.setConclusion(course.getConclusion());

        if(courseDTO.getAudience()!= null)
            thecourse.setAudience(course.getAudience());

        if(courseDTO.getPreRequisites()!= null)
            thecourse.setPreRequisites(course.getPreRequisites());

        if(courseDTO.getInstructorInfo()!= null)
            thecourse.setInstructorInfo(course.getInstructorInfo());

        if(courseDTO.getCourseImage()!= null)
            thecourse.setCourseImage(course.getCourseImage());

        if(courseDTO.getCourseVideo()!= null)
            thecourse.setCourseVideo(course.getCourseVideo());

        if(courseDTO.getPrice() != 0)
            thecourse.setPrice(course.getPrice());

        if(courseDTO.getProgress()!= 0)
            thecourse.setProgress(course.getProgress());

        if(courseDTO.getStatus()!= null)
            thecourse.setStatus(course.getStatus());

        if(courseDTO.getScore()!= 0)
            thecourse.setScore(course.getScore());

        thecourse.setType(course.isType());

        if(courseDTO.getStudyType()!= null)
            thecourse.setStudyType(course.getStudyType());

        thecourse.setStructure(course.getStructure());
        thecourse.setFree(course.isFree());

        if(courseDTO.getStartDate()!= null)
            thecourse.setStartDate(course.getStartDate());
        if(courseDTO.getEndDate()!= null)
            thecourse.setEndDate(course.getEndDate());
        if(courseDTO.getNbrAttendee() != 0)
            thecourse.setNbrAttendee(course.getNbrAttendee());
        thecourse.setAcceptClassroom(course.isAcceptClassroom());
        if(courseDTO.getSyllabus() != null)
            thecourse.setSyllabus(course.getSyllabus());
        Course newCourse = reposCourse.save(thecourse);
        return  mapper.map(newCourse, CourseDTO.class);
    }
    @Override
    public void deleteCourse(String  idCourse) {
        Course course = reposCourse.findById(idCourse).orElseGet(Course::new);
        reposCourse.delete(course);
    }

    @Override
    public void archiveCourse(String idCourse) {
        Course course = reposCourse.findById(idCourse).orElseGet(Course::new);
        course.setArchived(true);
        reposCourse.save(course);
    }








}