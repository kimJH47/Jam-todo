package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoRequest {

    private String title;
    private Long order;
    private Boolean completed;


    public TodoEntity toEntity() {
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setCompleted(this.completed);
        todoEntity.setTitle(this.title);
        todoEntity.setOrder(this.order);
        return todoEntity;
    }
}
