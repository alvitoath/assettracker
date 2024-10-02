package propensi.project.Assettrackr.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import propensi.project.Assettrackr.model.UserModel;
import propensi.project.Assettrackr.repository.*;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDb userDb;

    @Autowired
    private AdminDb adminDb;

    @Autowired
    private DeveloperDb developerDb;

    @Autowired
    private OperatorDb operatorDb;

    @Autowired
    private SecurityOfficerDb securityOfficerDb;

    @Override
    public UserModel getUserById(String id) {
        Optional<UserModel> user = userDb.findUserById(id);
        return user.orElse(null);
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public Boolean checkUsernameRegistered(String username) {
        return null;
    }

    @Override
    public UserModel getUserByUsername(String username) {
        return null;
    }

    @Override
    public Boolean checkEmailRegistered(String email) {
        return null;
    }

    @Override
    public String encrypt(String password) {
        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
