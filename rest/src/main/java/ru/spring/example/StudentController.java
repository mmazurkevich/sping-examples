package ru.spring.example;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spring.example.model.Student;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Mikhail on 17.12.2016.
 */
@RestController
public class StudentController {

    @RequestMapping(path = "/api/list", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Student>> getStudentList(){
        return new ResponseEntity<>(Arrays.asList(getStudent()), HttpStatus.OK);
    }

    @RequestMapping(path = "/api/{id}", method = RequestMethod.GET)
    public ResponseEntity<Student> getStudentByPath(@PathVariable int studentId){
        Student student = getStudent();
        student.setId(studentId);
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
