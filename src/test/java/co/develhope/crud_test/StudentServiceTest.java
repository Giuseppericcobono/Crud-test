package co.develhope.crud_test;

import co.develhope.crud_test.entities.Students;
import co.develhope.crud_test.repositories.StudentsRepository;
import co.develhope.crud_test.services.StudentsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles(value = "test")
public class StudentServiceTest {

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private StudentsRepository studentsRepository;

    @Test
    void checkStudentWorking() throws Exception {
        Students student = new Students();
        student.setWorking(true);
        student.setName("Giuseppe");
        student.setSurname("Riccobono");

        Students studentFromDB = studentsRepository.save(student);
        assertThat(studentFromDB).isNotNull();
        assertThat(studentFromDB.getId()).isNotNull();

        Students studentFromService = studentsService.setStudentWorkingStatus(student.getId(), true);
        assertThat(studentFromService.getId()).isNotNull();
        assertThat(studentFromService.getWorking()).isTrue();

        Students studentFind = studentsRepository.findById(studentFromDB.getId()).get();
        assertThat(studentFind).isNotNull();
        assertThat(studentFind.getId()).isNotNull();
        assertThat(studentFind.getId()).isEqualTo(studentFromDB.getId());
        assertThat(studentFind.getWorking()).isTrue();
    }
}
