package co.develhope.crud_test.controllers;

import co.develhope.crud_test.entities.Students;
import co.develhope.crud_test.repositories.StudentsRepository;
import co.develhope.crud_test.services.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentsRepository studentsRepository;

    @Autowired
    private StudentsService studentsService;

    @PostMapping("/create")
    public @ResponseBody Students createStudent (@RequestBody Students student) {
        return studentsRepository.save(student);
    }

    @GetMapping("/list")
    public List<Students> studentsList () {
        return studentsRepository.findAll();
    }

    @GetMapping("/search/{id}")
    public @ResponseBody Optional<Students> searchSingleStudent (@PathVariable Integer id) {
        Optional<Students> student = studentsRepository.findById(id);
        return student;
    }

    @PutMapping("/update/{id}")
    public @ResponseBody Students updateStudent (@PathVariable Integer id, @RequestBody Students student){
       student.setId(id);
       return studentsRepository.save(student);
    }

    @PutMapping("/working/{id}/activation")
    public @ResponseBody Students studentWorking (@PathVariable Integer id, @RequestParam("activated") Boolean working){
       return studentsService.setStudentWorkingStatus(id, working);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable Integer id){
        studentsRepository.deleteById(id);
    }
}
