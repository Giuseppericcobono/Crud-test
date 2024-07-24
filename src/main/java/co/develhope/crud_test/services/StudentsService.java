package co.develhope.crud_test.services;

import co.develhope.crud_test.entities.Students;
import co.develhope.crud_test.repositories.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentsService {

    @Autowired
    private StudentsRepository studentsRepository;

    public Students setStudentWorkingStatus(Integer id, Boolean isActive){
        Optional<Students> student = studentsRepository.findById(id);
        if(!student.isPresent()) return null;
        student.get().setWorking(isActive);
        return studentsRepository.save(student.get());
    }
}
