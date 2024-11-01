package propensi.project.Assettrackr.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "server_changes")
public class ServerChanges {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "server_id", nullable = false)
    private Server server;

    @NotNull
    @Column(name = "problem", nullable = false)
    private String problem; //Request for update, Power Outages, Natural Element Interference, Hardware Damage ,Cybersecurity Issues, System Overload, Configuration Settings,  Network Glitches, Others

    @NotNull
    @Column(name = "notes", nullable = false)
    private String notes;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status; // One process atau draft
}