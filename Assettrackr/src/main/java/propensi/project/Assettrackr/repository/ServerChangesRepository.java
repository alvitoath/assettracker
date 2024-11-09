package propensi.project.Assettrackr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import propensi.project.Assettrackr.model.ServerChanges;

import java.util.List;

public interface ServerChangesRepository extends JpaRepository<ServerChanges, String> {
//    @Query("SELECT * FROM ")
//    List<ServerChanges> findServerChangesByDivisi(String divisi);
}
