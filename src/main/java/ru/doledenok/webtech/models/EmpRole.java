package ru.doledenok.webtech.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "employee_role")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class EmpRole implements GenericEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "emp_role_id")
    public Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(nullable = false, name = "emp_id")
    @NonNull
    public Employee emp;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(nullable = false, name = "proj_role_id")
    @NonNull
    public ProjectRole proj_role;

    @Column(name = "start_date")
    @NonNull
    public java.sql.Date start;

    @Column(name = "end_date")
    public java.sql.Date end;
}
