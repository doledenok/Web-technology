package ru.doledenok.webtech.models;

import com.sun.istack.NotNull;
import lombok.*;
import javax.persistence.*;

import java.sql.Date;
@Entity
@Table(name = "employee")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Employee implements GenericEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "emp_id")
    public Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(name = "pos_id")
    public Position position;

    @Column(name = "name")
    @NotNull
    public String name;

    @Column(name = "education")
    public String education;

    @Column(name = "address")
    public String address;

    @Column(name = "work_experience")
    public String workExperience;

    @Column(name = "status")
    public String status;

    @Column(name = "date_of_birth")
    public Date dateOfBirth;

    public String getPositionName() {
        if (position == null)
            return "";
        else
            return position.getName();
    }
}
