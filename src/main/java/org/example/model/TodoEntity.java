package org.example.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, name = "todo_order")
    private Long order;
    @Column(nullable = false)
    private Boolean completed;


    public TodoEntity update(TodoRequest todoRequest) {
        if (todoRequest.getCompleted() != null) {
            this.completed = todoRequest.getCompleted();
        }
        if (todoRequest.getTitle() != null) {
            this.title = todoRequest.getTitle();
        }
        if (todoRequest.getOrder() != null) {
            this.order = todoRequest.getOrder();
        }
        return this;
    }

}
