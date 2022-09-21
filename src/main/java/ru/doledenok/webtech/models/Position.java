package ru.doledenok.webtech.models;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "position")
@Getter
@Setter
@ToString
@NoArgsConstructor
// FIXME: delete this?
//@RequiredArgsConstructor
@AllArgsConstructor
public class Position implements GenericEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "pos_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String desc;
}
