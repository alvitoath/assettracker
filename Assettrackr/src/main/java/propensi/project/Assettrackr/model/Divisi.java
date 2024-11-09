package propensi.project.Assettrackr.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "divisi")
public class Divisi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama", nullable = false)
    private String nama;

    @Column(name = "keterangan")
    private String keterangan;

    @JsonIgnore
    @OneToMany(mappedBy = "divisi", fetch = FetchType.LAZY)
    private List<Server> listServer;

    @JsonIgnore
    @OneToMany(mappedBy = "divisi", fetch = FetchType.LAZY)
    private List<UserModel> listUser;

    @Lob
    @Column(name = "foto")
    private byte[] foto;
}

