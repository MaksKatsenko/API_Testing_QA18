package dto;

import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor

public class ContactDTO {
    String id;
    String name;
    String lastName;
    String email;
    String phone;
    String address;
    String description;

}
