package com.chat.Server.repository;

import com.chat.Server.payload.Message;
import com.mysql.cj.Messages;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//The `MessageRepository` interface defines methods for accessing and manipulating message data
//in the database. It extends the Spring Data JPA `JpaRepository` interface for generic CRUD operations
//on the `Message` entity.

//methods `safeFindBySender` and `safeFindByReceiver`, which
//wrap the database queries in try-catch blocks to handle database-related exceptions.
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySender(String senderName);

}
