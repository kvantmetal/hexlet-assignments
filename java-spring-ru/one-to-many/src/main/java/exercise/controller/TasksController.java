package exercise.controller;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.TaskMapper;
import exercise.model.Task;
import exercise.model.User;
import exercise.repository.TaskRepository;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    // BEGIN
    @GetMapping("")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskDTO> taskDTOS = tasks.stream().map(t -> taskMapper.map(t)).toList();
        return ResponseEntity.ok(taskDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new ResourceNotFoundException("Task id %d not found");
        }

        Task task = optionalTask.get();

        return ResponseEntity.ok(taskMapper.map(task));
    }

    @PostMapping("")
    public ResponseEntity<TaskDTO> saveTask(@RequestBody TaskCreateDTO taskCreateDTO) {
        Task task = taskMapper.map(taskCreateDTO);
        taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskMapper.map(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable long id, @RequestBody TaskUpdateDTO taskUpdateDTO) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new ResourceNotFoundException("Task id %d not found");
        }

        Task taskDb = optionalTask.get();

        taskMapper.update(taskUpdateDTO, taskDb);
        Optional<User> byId = userRepository.findById(taskUpdateDTO.getAssigneeId());
        taskDb.setAssignee(byId.get());
        taskRepository.save(taskDb);
        return ResponseEntity.ok(taskMapper.map(taskDb));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new ResourceNotFoundException("Task id %d not found");
        }
        taskRepository.deleteById(id);

    }
    // END
}
