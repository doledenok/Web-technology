package ru.doledenok.webtech.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "project_roles")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class ProjectRole implements GenericEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "proj_role_id")
    public Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(nullable = false, name = "proj_id")
    @NonNull
    public Project proj;

    @Column(name = "role_name")
    public String name;

    @Column(name = "required_count")
    public Long required_count;

    @Column(name = "real_count")
    public Long real_count;

    @Column(name = "status")
    public String status;

    @Column(name = "description")
    public String description;
}
