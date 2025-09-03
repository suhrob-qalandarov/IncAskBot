package org.exp.incaskbot.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.exp.incaskbot.model.enums.State;
import org.exp.incaskbot.model.base.Auditable;

import java.util.List;
import java.util.ArrayList;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sessions")
public class Session extends Auditable {
    @Id
    private Long chatId;

    private Integer lastMessageId;
    private String contactUri;
    private Boolean isAdmin;

    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToMany
    private List<IncChat> chatList = new ArrayList<>();

    @OneToOne
    private User user;
}
