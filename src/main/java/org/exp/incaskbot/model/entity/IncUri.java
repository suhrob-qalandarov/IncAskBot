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
@Table(name = "inc_uris")
public class IncUri extends BaseEntity {

    @Column(unique = true)
    private String temporaryReferralUri;
    private Boolean isActive;
    private Boolean isBlocked;

    private LocalDateTime expiresAt;

    /*@ManyToOne
    private Session session;*/
}
