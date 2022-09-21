package ru.doledenok.webtech.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "payment_policy")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Policy implements GenericEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "policy_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(name = "pos_id")
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(nullable = true, name = "role_id")
    private ProjectRole role;

    @Column(name = "sum")
    @NonNull
    private Long sum;

    @Column(name = "regularity")
    private String regularity;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    @NonNull
    private String description;

    public String getPositionName() {
        if (position == null)
            return "";
        else
            return position.getName();
    }
}
