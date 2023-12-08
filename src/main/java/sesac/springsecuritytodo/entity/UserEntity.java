package sesac.springsecuritytodo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="User", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name="id", updatable = false)
    private Long id; // 고유 id

    @Column(name="username", updatable = false)
    private String username;

    @Column(name="email", updatable = false)
    private String email; // 사용자 이메일 = 회원 아이디

    @Column(name="password", updatable = false)
    private String password;
}
