package propensi.project.Assettrackr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.project.Assettrackr.model.DivisiModel;

@Repository
public interface DivisiDb extends JpaRepository<DivisiModel, Integer> {
}
