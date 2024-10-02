package propensi.project.Assettrackr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.project.Assettrackr.model.SoftwareModel;

@Repository
public interface SoftwareDb extends JpaRepository<SoftwareModel, Integer> {
}
