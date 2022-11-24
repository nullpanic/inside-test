package dev.nullpanic.insidetest.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private Long id;
    private Long user_id;
    private String message;
    private String name;
}
