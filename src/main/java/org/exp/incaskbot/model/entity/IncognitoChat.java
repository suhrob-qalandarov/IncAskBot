package org.exp.incaskbot.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.exp.incaskbot.model.base.BaseEntity;
import org.exp.incaskbot.model.enums.IncChatStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inc_chats")
public class IncognitoChat extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private IncChatStatus status;

    @ManyToOne
    @JoinColumn(name = "from_id", nullable = false)
    private Session from;

    @ManyToOne
    @JoinColumn(name = "to_id", nullable = false)
    private Session to;

    @OneToOne
    private Message message;
}
