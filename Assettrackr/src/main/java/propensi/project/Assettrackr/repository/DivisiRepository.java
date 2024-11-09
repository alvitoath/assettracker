package propensi.project.Assettrackr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.project.Assettrackr.model.Divisi;

import java.util.Optional;

@Repository
public interface DivisiRepository extends JpaRepository<Divisi, String> {

    Optional<Divisi> findByNama(String nama);
}

