package propensi.project.Assettrackr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.project.Assettrackr.model.UserModel;

import java.util.Optional;

import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findByEmail(String email);

}

