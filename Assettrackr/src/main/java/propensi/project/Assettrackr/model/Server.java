package propensi.project.Assettrackr.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "server")
public class Server {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 50)
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "divisi_id", nullable = false) // Foreign key column
    private Divisi divisi;

    @NotNull
    @Size(max = 100)
    @Column(name = "lokasi", nullable = false)
    private String lokasi;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @NotNull
    @Size(max = 50)
    @Column(name = "sistem_operasi", nullable = false)
    private String sistemOperasi;

    @NotNull
    @Size(max = 50)
    @Column(name = "bahasa_pemrograman", nullable = false)
    private String bahasaPemrograman;

    @NotNull
    @Size(max = 20)
    @Column(name = "versi_bahasa", nullable = false)
    private String versiBahasa;

    @NotNull
    @Size(max = 50)
    @Column(name = "framework", nullable = false)
    private String framework;

    @NotNull
    @Size(max = 20)
    @Column(name = "versi_framework", nullable = false)
    private String versiFramework;

    @JsonIgnore
    @OneToMany(mappedBy = "server", fetch = FetchType.LAZY)
    private List<ServerChanges> listServerChanges;
}
