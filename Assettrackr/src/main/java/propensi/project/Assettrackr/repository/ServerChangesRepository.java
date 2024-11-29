package propensi.project.Assettrackr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import propensi.project.Assettrackr.model.ServerChanges;

import java.util.List;

public interface ServerChangesRepository extends JpaRepository<ServerChanges, String> {
    @Query(value = "SELECT * FROM server_changes sc, server se WHERE sc.server_id = se.id AND se.divisi_id = :divisiId", nativeQuery = true)
    List<ServerChanges> findServerChangesByDivisi(String divisiId);

    @Query(value = "SELECT * FROM server_changes sc, server se, divisi di WHERE sc.server_id = se.id AND se.divisi_id = di.id AND di.nama= :divisiName", nativeQuery = true)
    List<ServerChanges> findServerChangesByDivisiName(String divisiName);

    @Query(value = "SELECT * FROM server_changes sc, server se WHERE sc.server_id = se.id AND se.divisi_id = :divisiId AND sc.status = 'Solved'", nativeQuery = true)
    List<ServerChanges> findserverChangesFinishedByDivisiId(String divisiId);
}
