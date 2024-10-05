package propensi.project.Assettrackr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.project.Assettrackr.model.Divisi;

@Repository
public interface DivisiRepository extends JpaRepository<Divisi, Integer> {
}
