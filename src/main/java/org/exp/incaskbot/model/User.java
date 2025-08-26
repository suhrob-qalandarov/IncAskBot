package org.exp.incaskbot.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;
    private String fullName;
    private String username;

    @ElementCollection
    @CollectionTable(
            name = "user_chats",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "chat_payload")
    private List<String> chats = new ArrayList<>();
}
