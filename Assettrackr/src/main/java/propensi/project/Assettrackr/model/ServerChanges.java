package propensi.project.Assettrackr.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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
    @Temporal(TemporalType.DATE)
    @Column(name = "tanggal_dibuat", nullable = false)
    private Date tanggalDibuat;

    @Temporal(TemporalType.DATE)
    @Column(name = "tanggal_selesai")
    private Date tanggalSelesai;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ServerChangesStatus status; // One process atau draft

    @OneToOne(mappedBy = "serverChanges")
    private ChangesSolution solution;

    @ManyToMany
    @JoinTable(
            name = "server_changes_developers",
            joinColumns = @JoinColumn(name = "server_changes_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id")
    )
    private List<Developer> developers;

    @ManyToOne
    @JoinColumn(name = "anggota_id")
    private UserModel anggota;

}