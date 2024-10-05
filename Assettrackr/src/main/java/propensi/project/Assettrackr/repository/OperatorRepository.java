package propensi.project.Assettrackr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.project.Assettrackr.model.Operator;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, String> {
}
