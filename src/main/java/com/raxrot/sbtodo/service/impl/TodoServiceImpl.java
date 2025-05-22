package com.raxrot.sbtodo.service.impl;

import com.raxrot.sbtodo.dto.TodoDTO;
import com.raxrot.sbtodo.entity.Todo;
import com.raxrot.sbtodo.exception.ApiException;
import com.raxrot.sbtodo.repository.TodoRepository;
import com.raxrot.sbtodo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class TodoServiceImpl implements TodoService {

    private final ModelMapper modelMapper;
    private final TodoRepository todoRepository;

    public TodoServiceImpl(ModelMapper modelMapper, TodoRepository todoRepository) {
        this.modelMapper = modelMapper;
        this.todoRepository = todoRepository;
    }

    @Override
    public TodoDTO createTodo(TodoDTO todoDTO) {
        log.info("Creating new todo with title: {}", todoDTO.getTitle());
        Todo todo = modelMapper.map(todoDTO, Todo.class);
        Todo savedTodo = todoRepository.save(todo);
        log.debug("Saved todo entity: {}", savedTodo);
        return modelMapper.map(savedTodo, TodoDTO.class);
    }

    @Override
    public List<TodoDTO> getAllTodos() {
        log.info("Fetching all todos");
        List<Todo> todos = todoRepository.findAll();
        log.debug("Fetched {} todos", todos.size());
        return todos.stream()
                .map(todo -> modelMapper.map(todo, TodoDTO.class))
                .toList();
    }

    @Override
    public TodoDTO getTodoById(Long id) {
        log.info("Fetching todo by id: {}", id);
        Todo todo = getTodoOrElseThrowApiException(id);
        log.debug("Found todo: {}", todo);
        return modelMapper.map(todo, TodoDTO.class);
    }

    @Override
    public TodoDTO updateTodo(Long id, TodoDTO todoDTO) {
        log.info("Updating todo with id: {}", id);
        Todo todoFromDb = todoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cannot update: todo with id {} not found", id);
                    return new ApiException("No todo with id: " + id);
                });

        todoFromDb.setTitle(todoDTO.getTitle());
        todoFromDb.setDescription(todoDTO.getDescription());
        todoFromDb.setCompleted(todoDTO.isCompleted());

        Todo savedTodo = todoRepository.save(todoFromDb);
        log.debug("Updated todo entity: {}", savedTodo);
        return modelMapper.map(savedTodo, TodoDTO.class);
    }

    @Override
    public void deleteTodo(Long id) {
        log.info("Deleting todo with id: {}", id);
        if (!todoRepository.existsById(id)) {
            log.warn("Cannot delete: todo with id {} does not exist", id);
            throw new ApiException("No todo with id: " + id);
        }
        todoRepository.deleteById(id);
        log.info("Todo with id {} deleted successfully", id);
    }

    @Override
    public TodoDTO completeTodo(Long id) {
        log.info("Marking todo as completed, id: {}", id);
        Todo todo = getTodoOrElseThrowApiException(id);
        if (todo.isCompleted()) {
            log.info("Todo with id {} is already completed", id);
        } else {
            todo.setCompleted(true);
            Todo savedTodo = todoRepository.save(todo);
            log.info("Todo with id {} marked as completed", id);
            log.debug("Completed todo entity: {}", savedTodo);
            return modelMapper.map(savedTodo, TodoDTO.class);
        }
        return modelMapper.map(todo, TodoDTO.class);
    }

    @Override
    public TodoDTO inCompleteTodo(Long id) {
        log.info("Marking todo as incomplete, id: {}", id);
        Todo todo = getTodoOrElseThrowApiException(id);
        if (!todo.isCompleted()) {
            log.info("Todo with id {} is already incomplete", id);
        } else {
            todo.setCompleted(false);
            Todo savedTodo = todoRepository.save(todo);
            log.info("Todo with id {} marked as incomplete", id);
            log.debug("Incompleted todo entity: {}", savedTodo);
            return modelMapper.map(savedTodo, TodoDTO.class);
        }
        return modelMapper.map(todo, TodoDTO.class);
    }

    private Todo getTodoOrElseThrowApiException(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Todo with id {} not found", id);
                    return new ApiException("No todo with id: " + id);
                });
    }
}