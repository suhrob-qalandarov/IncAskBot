package org.exp.incaskbot.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.exp.incaskbot.model.base.Auditable;

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

    private long coin;

    @OneToOne
    private UserStatistics statistics;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<IncognitoUri> urlList = new ArrayList<>();
}
