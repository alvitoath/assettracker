package propensi.project.Assettrackr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.project.Assettrackr.model.ServerChanges;

import java.util.UUID;

public interface ServerChangesRepository extends JpaRepository<ServerChanges, UUID> {
}
