package org.exp.incaskbot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;
    private String fullName;
    private String username;
    private String url;

    private Integer lastMessageId;

    private String last;

    private Boolean isAdmin;
}
