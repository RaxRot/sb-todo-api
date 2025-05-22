package com.raxrot.sbtodo.repository;

import com.raxrot.sbtodo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo,Long> {
}
