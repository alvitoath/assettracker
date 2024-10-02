package propensi.project.Assettrackr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.project.Assettrackr.model.RoleModel;
import propensi.project.Assettrackr.model.UserModel;

import java.util.Optional;

public interface RoleDb extends JpaRepository<RoleModel, Integer> {
    Optional<RoleModel> findRoleById(Integer id);
}
