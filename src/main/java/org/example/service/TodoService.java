package org.example.service;


import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.model.TodoEntity;
import org.example.model.TodoRequest;
import org.example.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoEntity add(TodoRequest request) {
        return todoRepository.save(request.toEntity());
    }

    public TodoEntity searchById(Long id) {
        return todoRepository.findById(id)
                             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<TodoEntity> searchAll() {
        return todoRepository.findAll();
    }

    public TodoEntity updateById(Long id, TodoRequest todoRequest) {
        TodoEntity todoEntity = this.searchById(id);
        return todoEntity.update(todoRequest);
    }

    public void deleteById(Long id) {
        todoRepository.deleteById(id);

    }

    public void deleteAll() {
        todoRepository.deleteAll();
    }

}
