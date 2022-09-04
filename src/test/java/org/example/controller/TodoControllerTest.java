package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.TodoEntity;
import org.example.model.TodoRequest;
import org.example.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    MockMvc mockMvc;
    private TodoEntity expected;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private TodoService todoService;

    @BeforeEach
    void setup() {
        this.expected = new TodoEntity();
        this.expected.setCompleted(false);
        this.expected.setTitle("Test");
        this.expected.setId(1234L);
        this.expected.setOrder(1L);

    }
    @Test
    public void create() throws Exception {

        //given
        //when
        when(todoService.add(any(TodoRequest.class))).then(invocation -> {
            TodoRequest todoRequest = invocation.getArgument(0, TodoRequest.class);
            return new TodoEntity(this.expected.getId(),
                    todoRequest.getTitle(),
                    expected.getOrder(),
                    expected.getCompleted());
        });
        TodoRequest todoRequest = new TodoRequest();
        todoRequest.setTitle("Any Title");
        //then
        mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON)
                                 .content(mapper.writeValueAsString(todoRequest)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.title").value("Any Title"));

    }
    @Test
    public void readOne() throws Exception{
        //given
        given(todoService.searchById(anyLong())).willReturn(expected);
        //when
        //then
        mockMvc.perform(get("/1234").accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.title").value(expected.getTitle()))
               .andExpect(jsonPath("$.order").value(expected.getOrder()))
               .andExpect(jsonPath("$.completed").value(expected.getCompleted()));

    }

}