package dev.nullpanic.insidetest.dto;

import dev.nullpanic.insidetest.persist.models.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String secret;
    private Set<Message> messages;
}
