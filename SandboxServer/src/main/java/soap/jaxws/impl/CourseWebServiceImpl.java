package soap.jaxws.impl;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import soap.dto.CourseDTO;
import soap.entity.Course;
import soap.jaxws.CourseWebService;
import soap.service.CourseService;

import javax.annotation.PostConstruct;
import javax.jws.WebService;
import java.util.Arrays;

/**
 * Created by EGBoldyr on 28.03.18.
 */

@Component
@WebService(serviceName = "CourseService", portName = "CoursePort", endpointInterface = "soap.jaxws.CourseWebService")
public class CourseWebServiceImpl implements CourseWebService {

    private Logger log;

    @Autowired
    private CourseService service;

    @Autowired
    private DozerBeanMapper mapper;

    @PostConstruct
    private void initialize() {
        log = LoggerFactory.getLogger(CourseWebServiceImpl.class);
        log.info("Initialize - Course service...");
    }

    @Override
    public boolean newCourse(String title) {
        log.info("Starting course service... CREATE OPTION.");
        return service.create(title);
    }

    @Override
    public CourseDTO findByTitle(String title) {
        log.info("Starting find course by TITLE option...");
        Course course = service.findByTitle(title);
        return mapper.map(course, CourseDTO.class);
    }

    @Override
    public CourseDTO[] findCoursesPart(Integer from) {
        log.info("Receiving courses... START");
        Course[] courses = service.findCoursesPart(from, 4);
        log.info("Receiving courses... COMPLETE");
        CourseDTO[] body = new CourseDTO[courses.length];
        for (int i = 0; i < courses.length; i++) {
            body[i] = mapper.map(courses[i], CourseDTO.class);
        }
        log.info("Convert Course -> CourseDTO COMPLETE. " + body.length + " items.");
        return body;
    }

    @Override
    public CourseDTO[] allCourses() {
        log.info("Receiving all courses... START");
        log.info("Receiving courses... START");
        Course[] courses = service.findAll();
        log.info("Receiving courses... COMPLETE");
        CourseDTO[] body = new CourseDTO[courses.length];
        for (int i = 0; i < courses.length; i++) {
            body[i] = mapper.map(courses[i], CourseDTO.class);
        }
        log.info("Convert Course -> CourseDTO COMPLETE. " + courses.length + " items.");
        return body;
    }
}
