package propensi.project.Assettrackr.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "server")
public class ServerModel {
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

    @ManyToMany
    @JoinTable(name = "server_developer",
            joinColumns = @JoinColumn(name = "id_server", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_developer", referencedColumnName = "id"))
    private List<DeveloperModel> listDeveloper;

    @ManyToOne
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "software_id", nullable = false) // Foreign key column
    private SoftwareModel software;

    @ManyToOne
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "divisi_id", nullable = false) // Foreign key column
    private DivisiModel divisi;
}
