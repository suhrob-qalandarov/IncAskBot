package org.exp.incaskbot.repository;

import jakarta.transaction.Transactional;
import org.exp.incaskbot.model.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query(
            value = """
            UPDATE users
            SET full_name   = :fullName,
                username    = :username
            WHERE id = :id
            RETURNING *
        """,
            nativeQuery = true
    )
    User updateAndReturnUserInfo(
            @Param("id") Long id,
            @Param("fullName") String fullName,
            @Param("username") String username
    );

    @Modifying
    @Query(value = "INSERT INTO user_chats(user_id, chat_payload) VALUES (:userId, :chatPayload)", nativeQuery = true)
    void addChatToUser(@Param("userId") Long userId, @Param("chatPayload") String chatPayload);

    @Query(value = "SELECT COUNT(*) > 0 FROM user_chats WHERE user_id = :userId AND chat_payload = :chatPayload", nativeQuery = true)
    boolean existsChat(@Param("userId") Long userId, @Param("chatPayload") String chatPayload);

}