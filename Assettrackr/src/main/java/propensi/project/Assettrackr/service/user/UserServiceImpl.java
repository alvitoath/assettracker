package propensi.project.Assettrackr.service.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import propensi.project.Assettrackr.model.Divisi;
import propensi.project.Assettrackr.model.Role;
import propensi.project.Assettrackr.model.UserModel;
import propensi.project.Assettrackr.model.dto.*;
import propensi.project.Assettrackr.repository.*;
import propensi.project.Assettrackr.security.jwt.JwtUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DivisiRepository divisiRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public String login(LoginRequest request) throws RuntimeException{
        Optional<UserModel> userOpt = userRepository.findByUsername(request.getUsername());

        if (userOpt.isEmpty()){
            throw new RuntimeException("User is not found");
        }

        UserModel user = userOpt.get();

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())){
            return jwtUtils.generateJwtToken(user.getUsername());
        } else {
            throw new RuntimeException("Password is wrong");
        }
    }
    @Override
    public ListUserResponse getAllUser() {
        List<UserModel> userModels = userRepository.findAll();
        List<UserResponse> users = userModels.stream().map(userModel -> new UserResponse(
                        userModel.getId().toString(),
                        userModel.getUsername(),
                        userModel.getNama(),
                        userModel.getEmail(),
                        userModel.getRole().toString(),
                        userModel.getDivisi().getNama()))
                .collect(Collectors.toList());

        ListUserResponse response = new ListUserResponse();
        response.setUsers(users);
        return response;
    }

    @Override
    public UserResponse getDetailUser(Integer id) throws RuntimeException{
        Optional<UserModel> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) throw new RuntimeException("User tidak ditemukan");
        UserModel user = userOpt.get();

        UserResponse response = new UserResponse(
                user.getId().toString(),
                user.getUsername(),
                user.getNama(),
                user.getEmail(),
                user.getRole().toString(),
                user.getDivisi().getNama());
        return response;
    }

}
