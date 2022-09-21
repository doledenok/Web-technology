package ru.doledenok.webtech.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "positions_history")
@Getter
@Setter
// FIXME: можно ли это безнаказанно убрать?
//@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class PosHistory implements GenericEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "pos_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(nullable = false, name = "pos_id")
    @NonNull
    private Position pos;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(nullable = false, name = "emp_id")
    @NonNull
    private Employee emp;

    @Column(name = "start_date")
    @NonNull
    private java.sql.Date start;

    @Column(name = "end_date")
    private java.sql.Date end;

    public String toString(){
        return pos.getName() + ": " + start.toString() + " - " + (end == null ? "по сей день" : end.toString());
    }
}
