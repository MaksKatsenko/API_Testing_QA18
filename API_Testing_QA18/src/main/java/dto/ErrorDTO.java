package dto;

import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor

public class ErrorDTO {

    int status;
    String error;
    String message;
}
