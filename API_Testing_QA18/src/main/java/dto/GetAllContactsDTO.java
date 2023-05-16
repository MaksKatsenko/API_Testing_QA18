package dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor

public class GetAllContactsDTO {
    List<ContactDTO> contacts;
}
