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
    private String id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "server_id", nullable = false)
    private Server server;

    @NotNull
    @Column(name = "tipe_perbaikan", nullable = false)
    private String tipePerbaikan; //Request for update, Power Outages, Natural Element Interference, Hardware Damage ,Cybersecurity Issues, System Overload, Configuration Settings,  Network Glitches, Others

    @NotNull
    @Column(name = "detail_perbaikan", nullable = false)
    private String detailPerbaikan;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tanggal_dibuat", nullable = false)
    private Date tanggalDibuat;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tanggal_selesai", nullable = false)
    private Date tanggalSelesai;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status; // One process atau draft

}