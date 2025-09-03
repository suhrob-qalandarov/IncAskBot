package org.exp.incaskbot.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.exp.incaskbot.model.base.BaseEntity;
import org.exp.incaskbot.model.enums.TransactionType;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coin_transactions")
public class CoinTransaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
