package propensi.project.Assettrackr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.project.Assettrackr.model.UserModel;

import java.util.Optional;

import org.springframework.stereotype.Repository;



@Repository
public interface UserDb extends JpaRepository<UserModel, String> {
    Optional<UserModel> findUserById(String id);
    UserModel findByUsername(String username);
}


