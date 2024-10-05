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
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminDb;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private SecurityOfficerRepository securityOfficerRepository;

    @Override
    public UserModel getUserById(String id) {
        Optional<UserModel> user = userRepository.findUserById(id);
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
