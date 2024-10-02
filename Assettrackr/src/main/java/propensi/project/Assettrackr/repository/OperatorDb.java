package propensi.project.Assettrackr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.project.Assettrackr.model.OperatorModel;
import propensi.project.Assettrackr.model.UserModel;

@Repository
public interface OperatorDb extends JpaRepository<OperatorModel, String> {
}
