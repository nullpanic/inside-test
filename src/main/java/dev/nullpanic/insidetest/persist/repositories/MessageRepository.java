package dev.nullpanic.insidetest.persist.repositories;

import dev.nullpanic.insidetest.persist.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
