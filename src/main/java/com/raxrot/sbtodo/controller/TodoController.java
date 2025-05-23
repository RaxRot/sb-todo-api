package com.raxrot.sbtodo.controller;

import com.raxrot.sbtodo.dto.TodoDTO;
import com.raxrot.sbtodo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/todos")
@Tag(name = "TODO Controller", description = "Handles creation, retrieval, updating, deletion, and completion of TODO tasks")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Operation(summary = "Create a new TODO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Todo created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping
    public ResponseEntity<TodoDTO> createTodo(@Valid @RequestBody TodoDTO todoDTO) {
        log.info("POST /api/todos — Creating todo");
        TodoDTO created = todoService.createTodo(todoDTO);
        return ResponseEntity.status(201).body(created);
    }

    @Operation(summary = "Get all TODOs")
    @ApiResponse(responseCode = "200", description = "List of all todos")
    @GetMapping
    public ResponseEntity<List<TodoDTO>> getAllTodos() {
        log.info("GET /api/todos — Fetching all todos");
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @Operation(summary = "Get a TODO by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo found"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getTodoById(@PathVariable Long id) {
        log.info("GET /api/todos/{} — Fetching todo", id);
        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    @Operation(summary = "Update a TODO by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TodoDTO> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoDTO todoDTO) {
        log.info("PUT /api/todos/{} — Updating todo", id);
        return ResponseEntity.ok(todoService.updateTodo(id, todoDTO));
    }

    @Operation(summary = "Delete a TODO by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Todo deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        log.info("DELETE /api/todos/{} — Deleting todo", id);
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Mark a TODO as completed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo marked as completed"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoDTO> completeTodo(@PathVariable Long id) {
        log.info("PATCH /api/todos/{}/complete — Marking as completed", id);
        return ResponseEntity.ok(todoService.completeTodo(id));
    }

    @Operation(summary = "Mark a TODO as incomplete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo marked as incomplete"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @PatchMapping("/{id}/incomplete")
    public ResponseEntity<TodoDTO> inCompleteTodo(@PathVariable Long id) {
        log.info("PATCH /api/todos/{}/incomplete — Marking as incomplete", id);
        return ResponseEntity.ok(todoService.inCompleteTodo(id));
    }
}
