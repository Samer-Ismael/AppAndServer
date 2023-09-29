package com.chat.Server.repository;

import com.chat.Server.payload.Message;
import com.mysql.cj.Messages;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//The `MessageRepository` interface defines methods for accessing and manipulating message data
//in the database. It extends the Spring Data JPA `JpaRepository` interface for generic CRUD operations
//on the `Message` entity.

//methods `safeFindBySender` and `safeFindByReceiver`, which
//wrap the database queries in try-catch blocks to handle database-related exceptions.
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Message findBySender(String sender);
    Message findByReceiver(String receiver);

    default Message safeFindBySender(String sender) {
        try {
            return findBySender(sender);
        } catch (DataAccessException e) {
            // Handle database-related exceptions
            e.printStackTrace();
            return null;
        }
    }

    default Message safeFindByReceiver(String receiver) {
        try {
            return findByReceiver(receiver);
        } catch (DataAccessException e) {
            // Handle database-related exceptions
            e.printStackTrace();
            return null;
        }
    }
}
