package org.exp.incaskbot.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.exp.incaskbot.model.base.Auditable;
import org.exp.incaskbot.model.enums.State;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "session")
    private List<IncUri> urlList = new ArrayList<>();

    @ManyToMany
    private List<IncChat> chatList = new ArrayList<>();

    @OneToOne
    private User user;
}
