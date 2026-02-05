package com.example.employeeapp.controller;
import com.example.employeeapp.dto.TaskResponse;
import com.example.employeeapp.model.Task;
import com.example.employeeapp.model.User;
import com.example.employeeapp.repository.TaskRepository;
import com.example.employeeapp.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepo;
    private final UserRepository userRepo;

    public TaskController(TaskRepository taskRepo, UserRepository userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    // ✅ ADD TASK
    @PostMapping("/{userId}")
    public ResponseEntity<TaskResponse> addTask(
            @PathVariable Long userId,
            @RequestBody TaskResponse req) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTask(req.task);
        task.setStatus("PENDING");
        task.setDate(LocalDate.now());
        task.setUser(user);

        Task savedTask = taskRepo.save(task);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(savedTask));
    }

    // ✅ GET TASKS BY USER
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponse>> getTasks(@PathVariable Long userId) {

        List<TaskResponse> tasks = taskRepo.findByUser_Id(userId)
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{taskId}/{userId}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long taskId,
            @PathVariable Long userId) {

        int updated = taskRepo.updatedTask(taskId, userId);

        if (updated == 0) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Task not found or not owned by user");
        }

        return ResponseEntity.ok("Task marked as completed");
    }

    private TaskResponse toResponse(Task task) {
        TaskResponse res = new TaskResponse();
        res.id = task.getId();
        res.task = task.getTask();
        res.status = task.getStatus();
        res.date = task.getDate();
        res.userId = task.getUser().getId();
        return res;
    }
}
