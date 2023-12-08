package sesac.springsecuritytodo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="Todo")
public class TodoEntity {
    @Id // pk
    @GeneratedValue(strategy =  GenerationType.IDENTITY) // auto_increment
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name="userId", nullable = false)
    private String userId;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="done", nullable = false)
    private boolean done;
}
