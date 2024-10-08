package propensi.project.Assettrackr.model;



import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 50)
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;


    @ManyToOne
    @PrimaryKeyJoinColumn
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
}
