package org.exp.incaskbot.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.exp.incaskbot.model.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contents")
public class Content extends BaseEntity {

    private String note;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files = new ArrayList<>();

   /*
    @OneToOne(mappedBy = "message")
    private Message message;
    */

    @OneToOne
    private Session session;
}
