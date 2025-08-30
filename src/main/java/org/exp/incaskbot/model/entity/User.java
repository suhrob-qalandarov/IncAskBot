package org.exp.incaskbot.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.exp.incaskbot.model.base.Auditable;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends Auditable {
    @Id
    private Long id;
    private String fullName;
    private String username;

    @Builder.Default
    private Integer messageCount = 0;
    @Builder.Default
    private Integer linkClickCount = 0;
    @Builder.Default
    private Integer popularity = 0;

    @ElementCollection
    @CollectionTable(
            name = "user_chats",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "chat_payload")
    private List<String> chats = new ArrayList<>();
}
