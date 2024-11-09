package propensi.project.Assettrackr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import propensi.project.Assettrackr.model.ServerChanges;

import java.util.List;

public interface ServerChangesRepository extends JpaRepository<ServerChanges, String> {
    @Query(value = "SELECT * FROM server_chages sc, server se WHERE sc.server_id = se.id AND se.divisi_id = :divisiId", nativeQuery = true)
    List<ServerChanges> findServerChangesByDivisi(String divisiId);
}
