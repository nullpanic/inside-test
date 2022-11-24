package dev.nullpanic.insidetest.persist.models;

import dev.nullpanic.insidetest.enums.Roles;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SequenceGenerator(name = "ROLE_SEQ_GENERATOR", sequenceName = "ROLE_SEQ", allocationSize = 1)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ROLE_SEQ")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "role", length = 128, nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;
}
