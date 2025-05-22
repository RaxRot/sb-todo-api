package com.raxrot.sbtodo.controller;

import com.raxrot.sbtodo.dto.TodoDTO;
import com.raxrot.sbtodo.service.TodoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;
    public TodoController(TodoService todoService){
        this.todoService=todoService;
    }

    @PostMapping
    public ResponseEntity<TodoDTO> createTodo(@Valid @RequestBody TodoDTO todoDTO) {
        log.info("POST /api/todos — Creating todo");
        TodoDTO created = todoService.createTodo(todoDTO);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<List<TodoDTO>> getAllTodos() {
        log.info("GET /api/todos — Fetching all todos");
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getTodoById(@PathVariable Long id) {
        log.info("GET /api/todos/{} — Fetching todo", id);
        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDTO> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoDTO todoDTO) {
        log.info("PUT /api/todos/{} — Updating todo", id);
        return ResponseEntity.ok(todoService.updateTodo(id, todoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        log.info("DELETE /api/todos/{} — Deleting todo", id);
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build(); // 204
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoDTO> completeTodo(@PathVariable Long id) {
        log.info("PATCH /api/todos/{}/complete — Marking as completed", id);
        return ResponseEntity.ok(todoService.completeTodo(id));
    }

    @PatchMapping("/{id}/incomplete")
    public ResponseEntity<TodoDTO> inCompleteTodo(@PathVariable Long id) {
        log.info("PATCH /api/todos/{}/incomplete — Marking as incomplete", id);
        return ResponseEntity.ok(todoService.inCompleteTodo(id));
    }
}
