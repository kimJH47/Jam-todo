package org.example.service;


import org.example.model.TodoEntity;
import org.example.model.TodoRequest;
import org.example.repository.TodoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;
    @InjectMocks
    private TodoService todoService;
    @Test
    void add() {
        when(todoRepository.save(any(TodoEntity.class))).then(returnsFirstArg());

        TodoRequest expected = new TodoRequest();
        expected.setTitle("Test");
        TodoEntity actual = todoService.add(expected);
        assertEquals(actual.getTitle(), expected.getTitle());
    }

    @Test
    void searchById() {
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setId(1234L);
        todoEntity.setTitle("title");
        todoEntity.setOrder(2L);
        todoEntity.setCompleted(false);
        Optional<TodoEntity> optionalTodoEntity = Optional.of(todoEntity);
        given(todoRepository.findById(anyLong())).willReturn(optionalTodoEntity);

        TodoEntity actual = todoService.searchById(1234L);
        TodoEntity expected = optionalTodoEntity.get();

        assertEquals(actual.getTitle(), expected.getTitle());
        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getOrder(), expected.getOrder());
        assertEquals(actual.getCompleted(), expected.getCompleted());
    }

    @Test
    public void searchByIdFailed() throws Exception{
        //given
        given(todoRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        //then
        assertThrows(ResponseStatusException.class, () ->{
            todoService.searchById(123L);
        });
    }
}