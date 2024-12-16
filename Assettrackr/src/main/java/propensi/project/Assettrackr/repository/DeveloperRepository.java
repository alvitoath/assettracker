package propensi.project.Assettrackr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.project.Assettrackr.model.Developer;

public interface DeveloperRepository extends JpaRepository<Developer, String> {
    boolean existsByNama(String nama);
}
