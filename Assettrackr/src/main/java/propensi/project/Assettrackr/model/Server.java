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
}
