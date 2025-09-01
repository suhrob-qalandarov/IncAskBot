package org.exp.incaskbot.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.exp.incaskbot.model.base.Auditable;

import java.time.LocalDateTime;
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
    private Boolean hasPremium;

    @Column(name = "main_referral_uri", unique = true, nullable = false)
    private String mainReferralUri;

    private int messageCount;
    private int linkClickCount;
    private int popularity;

    private int dailyMessageCount;
    private int dailyLinkClickCount;
    private int dailyPopularity;

    private long coin;

    @Builder.Default
    private LocalDateTime lastModifiedDailyDate = LocalDateTime.now();

    private List<IncChat> chats = new ArrayList<>();
}
