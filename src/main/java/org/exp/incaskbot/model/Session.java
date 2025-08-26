package org.exp.incaskbot.model;

import jakarta.persistence.*;
import lombok.*;
import org.exp.incaskbot.model.enums.State;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sessions")
public class Session {

    @Id
    private Long chatId;

    private Integer lastMessageId;

    @Column(unique = true, nullable = false)
    private String url;

    private String toMessageUrl;
    private Boolean isAdmin;

    @Enumerated(EnumType.STRING)
    private State state;

    @OneToOne
    private User user;
}
