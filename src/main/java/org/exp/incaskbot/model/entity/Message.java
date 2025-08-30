package org.exp.incaskbot.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.exp.incaskbot.model.base.BaseEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message extends BaseEntity {
    private Integer messageId;
    private Integer linkedMessageId;

    @OneToOne
    private Session to;

    @OneToOne
    private Session from;

    private LocalDateTime sentAt;

    @OneToOne(cascade = CascadeType.ALL)
    private Content content;
}
