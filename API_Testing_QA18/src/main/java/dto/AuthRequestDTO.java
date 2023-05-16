package dto;
import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor

public class AuthRequestDTO {

    String username;
    String password;

}
