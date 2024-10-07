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
@Table(name = "divisi")
public class Divisi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama", nullable = false)
    private String nama;

    @Column(name="keterangan")
    private String keterangan;

    @OneToMany(mappedBy = "divisi", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Server> listServer;

    @OneToMany(mappedBy = "divisi", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserModel> listUser;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

}
