package org.example.controller;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.model.TodoEntity;
import org.example.model.TodoRequest;
import org.example.model.TodoResponse;
import org.example.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest todoRequest) {
        if (ObjectUtils.isEmpty(todoRequest.getTitle())) {
            return ResponseEntity.badRequest()
                                 .build();
        }
        if (ObjectUtils.isEmpty(todoRequest.getOrder())) {
            todoRequest.setOrder(0L);
        }
        if (ObjectUtils.isEmpty(todoRequest.getCompleted())) {
            todoRequest.setCompleted(false);
        }
        return ResponseEntity.ok(new TodoResponse(todoService.add(todoRequest)));
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoResponse> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(new TodoResponse(todoService.searchById(id)));

    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> findAll() {
        List<TodoResponse> todoList = todoService.searchAll()
                                                .stream()
                                                .map(todoEntity -> new TodoResponse(todoEntity))
                                                .collect(Collectors.toList());

        return ResponseEntity.ok(todoList);
    }

    @PatchMapping("{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable Long id ,@RequestBody TodoRequest todoRequest) {
        return ResponseEntity.ok(new TodoResponse(todoService.updateById(id, todoRequest)));

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        System.out.println("DELETE ALL");
        todoService.deleteById(id);
        return ResponseEntity.ok()
                             .build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        todoService.deleteAll();
        return ResponseEntity.ok()
                             .build();
    }
}
