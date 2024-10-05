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
@Table(name = "software")
public class Software {
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
    @Column(name = "framework", nullable = false)
    private String framework;

    @NotNull
    @Size(max = 50)
    @Column(name = "versi", nullable = false)
    private String versi;

    // One-to-Many with ServerModel
    @OneToMany(mappedBy = "software", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Server> listServer;

}