package sesac.springsecuritytodo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String token; // jwt 저장 공간
    private String email;
    private String password;
    private String username;
    private Long id;
}
