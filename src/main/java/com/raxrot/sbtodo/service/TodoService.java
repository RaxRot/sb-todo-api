package com.raxrot.sbtodo.service;

import com.raxrot.sbtodo.dto.TodoDTO;

import java.util.List;

public interface TodoService {
  TodoDTO createTodo(TodoDTO todoDTO);
  List<TodoDTO>getAllTodos();
  TodoDTO getTodoById(Long id);
  TodoDTO updateTodo(Long id,TodoDTO todoDTO);
  void deleteTodo(Long id);
  TodoDTO completeTodo(Long id);
  TodoDTO inCompleteTodo(Long id);
}
