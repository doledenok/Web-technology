package ru.doledenok.webtech.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "payment")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Payment implements GenericEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "pay_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(nullable = false, name = "emp_id")
    @NonNull
    private Employee emp;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(nullable = false, name = "policy_id")
    @NonNull
    private Policy policy;

    @Column(name = "date")
    @NonNull
    private java.sql.Date start;
}
