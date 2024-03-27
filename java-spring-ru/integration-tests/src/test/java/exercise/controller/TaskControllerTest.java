package exercise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.model.Task;
import exercise.repository.TaskRepository;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// BEGIN
@SpringBootTest
@AutoConfigureMockMvc
// END
class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;


    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
            .andExpect(status().isOk())
            .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    private Task generateTask() {
        return Instancio.of(Task.class)
            .ignore(Select.field(Task::getId))
            .supply(Select.field(Task::getTitle), () -> faker.lorem().word())
            .supply(Select.field(Task::getDescription), () -> faker.lorem().paragraph())
            .create();
    }

    // BEGIN
    @Test
    public void testIndexById() throws Exception {
        Task task = Instancio.of(Task.class).create();
        Task savedTask = taskRepository.save(task);

        var result = mockMvc.perform(get("/tasks/" + String.valueOf(savedTask.getId())))
            .andExpect(status().isOk())
            .andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Task responseTask = om.readValue(contentAsString, Task.class);
        assertThat(responseTask).usingRecursiveComparison().isEqualTo(savedTask);
    }

    @Test
    public void shouldSaveTask() throws Exception {
        Task task = Instancio.of(Task.class).create();
        var postRequest = post("/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(task));
        var result = mockMvc.perform(postRequest)
            .andExpect(status().isCreated())
            .andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Task responseTask = om.readValue(contentAsString, Task.class);

        Task savedTask = taskRepository.findById(responseTask.getId()).get();

        assertThat(savedTask).usingRecursiveComparison().isEqualTo(responseTask);
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        Task task = Instancio.of(Task.class).ignore(Select.field(Task::getId)).create();
        Task task2 = Instancio.of(Task.class).ignore(Select.field(Task::getId)).create();
        Task savedTask = taskRepository.save(task);

        var request = put("/tasks/" + savedTask.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString((task2)));

        var result = mockMvc.perform(request)
            .andExpect(status().isOk())
            .andReturn();

        Task taskDb = taskRepository.findById(savedTask.getId()).get();

        assertThat(taskDb).usingRecursiveComparison().ignoringFields("id", "createdAt", "updatedAt").isEqualTo(task2);

    }

    @Test
    public void shouldDeleteTaskById() throws Exception {
        Task task = Instancio.of(Task.class).ignore(Select.field(Task::getId)).create();
        Task savedTask = taskRepository.save(task);

        var result = mockMvc.perform(delete("/tasks/" + savedTask.getId()))
            .andExpect(status().isOk());

        assertThat(taskRepository.findById(savedTask.getId())).isEmpty();

    }
    // END
}
