package com.spring.example.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class MetricsController {

    @Autowired
    private MetricRegistry metricRegistry;

    @RequestMapping(path = "/api/list", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Student>> getStudentList(){
        metricRegistry.meter("getStudentList").mark();
        return new ResponseEntity<>(Arrays.asList(getStudent()), HttpStatus.OK);
    }

    @RequestMapping(path = "/api/{id}", method = RequestMethod.GET)
    public ResponseEntity<Student> getStudentByPath(@PathVariable int studentId) throws InterruptedException {
        Timer.Context getStudentByPath = metricRegistry.timer("getStudentByPath").time();
        Student student = getStudent();
        student.setId(studentId);
        Thread.sleep(500);
        getStudentByPath.stop();
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    /**
     * Example url @see <a>localhost:8080/api?studentId=12</a>
     *
     * @param studentId Student identifier
     * @return serialized JSON object
     */
    @RequestMapping(path = "/api", method = RequestMethod.GET)
    public ResponseEntity<Student> getStudentByParam(@RequestParam int studentId){
        metricRegistry.counter("getStudentByParam").inc();
        Student student = getStudent();
        student.setId(studentId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @RequestMapping(path = "/api/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        return new ResponseEntity<>(new Student(), HttpStatus.OK);
    }

    private Student getStudent(){
        Student student = new Student();
        student.setId(23);
        student.setAge(25);
        student.setName("Spring-Example");
        return student;
    }
}
