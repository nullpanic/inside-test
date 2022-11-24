package dev.nullpanic.insidetest.persist.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "USER_MESSAGES_SEQ_GENERATOR", sequenceName = "USER_MESSAGES_SEQ", allocationSize = 1)
@Table(name = "user_messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_MESSAGES_SEQ")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @Column(name = "username", nullable = false)
    private String name;

    @Column(name = "message", nullable = false)
    private String message;

}
