package propensi.project.Assettrackr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.project.Assettrackr.model.Server;

@Repository
public interface ServerRepository extends JpaRepository<Server, Integer> {
    long countByDivisiId(Integer divisiId);

}
