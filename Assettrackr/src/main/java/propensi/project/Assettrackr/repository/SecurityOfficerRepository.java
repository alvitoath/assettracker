package propensi.project.Assettrackr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.project.Assettrackr.model.SecurityOfficer;

@Repository
public interface SecurityOfficerRepository extends JpaRepository<SecurityOfficer, String> {
}
