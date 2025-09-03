package org.exp.incaskbot.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.exp.incaskbot.model.base.Auditable;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_statistics")
public class UserStatistics extends Auditable {
    @Id
    private Long userId;

    private int messageCount;
    private int linkClickCount;
    private int popularity;

    private int dailyMessageCount;
    private int dailyLinkClickCount;
    private int dailyPopularity;

    @Builder.Default
    private LocalDateTime lastModifiedDailyDate = LocalDateTime.now();
}
