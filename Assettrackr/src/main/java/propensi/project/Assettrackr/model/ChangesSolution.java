package propensi.project.Assettrackr.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "changes_solution")
public class ChangesSolution {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SolutionStatus status; // draft atau finished

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "server_changes_id", referencedColumnName = "id")
    private ServerChanges serverChanges;

    @Column(name = "solution")
    private String solution;

}
