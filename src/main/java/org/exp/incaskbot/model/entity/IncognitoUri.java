package org.exp.incaskbot.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.exp.incaskbot.model.base.BaseEntity;
import org.exp.incaskbot.model.enums.UriType;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "incognito_uris", indexes = {
        @Index(name = "idx_uri", columnList = "uri"),
        @Index(name = "idx_is_active_expires_at", columnList = "isActive,expiresAt")
})
public class IncognitoUri extends BaseEntity {

    @Column(nullable = false, length = 255)
    @Size(min = 5, max = 50, message = "URI must be between 5 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "URI can only contain letters, numbers, and underscores")
    private String uri;

    @Builder.Default
    private Boolean isActive = true;
    @Builder.Default
    private Boolean isBlocked = false;

    @Enumerated(value = EnumType.STRING)
    private UriType type;

    private LocalDateTime expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
