package co.develhope.crud_test;

import co.develhope.crud_test.controllers.StudentController;
import co.develhope.crud_test.entities.Students;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
class StudentControllerTest {

    @Autowired
    private StudentController studentController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Students getStudentFromId(Integer id) throws Exception {
        MvcResult result = this.mockMvc.perform(get("/student/search/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        try {
            String userJSON = result.getResponse().getContentAsString();
            return objectMapper.readValue(userJSON, Students.class);
        } catch (Exception e) {
            return null;
        }
    }

    private Students createAStudent() throws Exception {
        Students student = new Students();
        student.setWorking(true);
        student.setName("Giuseppe");
        student.setSurname("Riccobono");

        return createAStudent(student);
    }

    private Students createAStudent(Students student) throws Exception {
        MvcResult result = createAStudentRequest(student);
        Students studentFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Students.class);
        return studentFromResponse;
    }

    private MvcResult createAStudentRequest() throws Exception {
        Students student = new Students();
        student.setId(1);
        student.setWorking(true);
        student.setName("Giuseppe");
        student.setSurname("Riccobono");

        return createAStudentRequest(student);
    }

    private MvcResult createAStudentRequest(Students student) throws Exception {
        if (student == null) return null;

        String studentJSON = objectMapper.writeValueAsString(student);

        return this.mockMvc.perform(post("/student/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void studentControllerLoads() {
        assertThat(studentController).isNotNull();
    }

    @Test
    @Order(1)
    void createAStudentTest() throws Exception {
        Students studentFromResponse = createAStudent();
        assertThat(studentFromResponse.getId()).isNotNull();
    }

    @Test
    @Order(2)
    void readStudentsList() throws Exception {
        createAStudent();

        MvcResult result = this.mockMvc.perform(get("/student/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<Students> studentFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        System.out.println("Students in database are: " + studentFromResponse.size());
        assertThat(studentFromResponse.size()).isNotZero();
    }

    @Test
    @Order(3)
    void readSingleStudent() throws Exception {
        Students student = createAStudent();

        assertThat(student.getId()).isNotNull();

        MvcResult result = this.mockMvc.perform(get("/student/search/" + student.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Students studentFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Students.class);
        assertThat(studentFromResponse.getId()).isEqualTo(student.getId());
    }

    @Test
    @Order(4)
    void updateStudent() throws Exception {
        Students student = createAStudent();

        assertThat(student.getId()).isNotNull();

        String newName = "Pietro";
        student.setName(newName);
        String studentJSON = objectMapper.writeValueAsString(student);

        MvcResult result = this.mockMvc.perform(put("/student/update/" + student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Students studentFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Students.class);
        assertThat(studentFromResponse.getId()).isEqualTo(student.getId());
        assertThat(studentFromResponse.getName()).isEqualTo(newName);
    }

    @Test
    @Order(5)
    void deleteStudent() throws Exception {
        Students student = createAStudent();

        assertThat(student.getId()).isNotNull();

        this.mockMvc.perform(delete("/student/delete/" + student.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Students studentFromResponse = getStudentFromId(student.getId());
        assertThat(studentFromResponse).isNull();
    }

    @Test
    @Order(6)
    void workingStudent() throws Exception {
        Students student = createAStudent();

        assertThat(student.getId()).isNotNull();

        MvcResult result = this.mockMvc.perform(put("/student/working/" + student.getId() + "/activation?activated=true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Students studentFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Students.class);
        assertThat(studentFromResponse).isNotNull();
        assertThat(studentFromResponse.getId()).isEqualTo(student.getId());
        assertThat(studentFromResponse.getWorking()).isEqualTo(true);

        Students studentFromResponseGet = getStudentFromId(student.getId());
        assertThat(studentFromResponseGet).isNotNull();
        assert studentFromResponseGet != null;
        assertThat(studentFromResponseGet.getId()).isEqualTo(student.getId());
        assertThat(studentFromResponseGet.getWorking()).isEqualTo(true);
    }
}