package propensi.project.Assettrackr.service.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import propensi.project.Assettrackr.model.Divisi;
import propensi.project.Assettrackr.model.Role;
import propensi.project.Assettrackr.model.UserModel;
import propensi.project.Assettrackr.model.dto.request.CreateUserRequest;
import propensi.project.Assettrackr.model.dto.request.LoginRequest;
import propensi.project.Assettrackr.model.dto.request.UserUpdateRequest;
import propensi.project.Assettrackr.model.dto.response.ListUserResponse;
import propensi.project.Assettrackr.model.dto.response.UserResponse;
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
    public String createUser(CreateUserRequest request) throws RuntimeException {
        // Check if username or email already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username sudah terdaftar");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email sudah terdaftar");
        }

        // Verify that the divisi exists
        Optional<Divisi> divisiOpt = divisiRepository.findByNama(request.getDivisi());
        if (divisiOpt.isEmpty()) {
            throw new RuntimeException("Divisi tidak tersedia");
        }

        Divisi divisi = divisiOpt.get();

        // Encrypt the user-provided password
        String encryptedPassword = encrypt(request.getPassword());

        // Create and save the user
        UserModel userModel = UserModel.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .nama(request.getNama())
                .divisi(divisi)
                .role(Role.valueOf(request.getRole()))
                .password(encryptedPassword)
                .build();

        userRepository.save(userModel);
//        if (userRepository.findByUsername(request.getUsername()).isPresent()){
//            throw new RuntimeException("Username sudah terdaftar");
//        } else if (userRepository.findByEmail(request.getEmail()).isPresent()) {
//            throw new RuntimeException("Email sudah terdaftar");
//        }
//
//        Optional<Divisi> divisiOpt = divisiRepository.findByNama(request.getDivisi());
//        if (divisiOpt.isEmpty()){
//            throw new RuntimeException("Divisi tidak tersedia");
//        }
//
//        Divisi divisi = divisiOpt.get();
//
//        String password = randomPass();
//        UserModel userModel = userRepository.save(UserModel.builder()
//                .email(request.getEmail())
//                .username(request.getUsername())
//                .nama(request.getNama())
//                .divisi(divisi)
//                .role(Role.valueOf(request.getRole()))
//                .password(encrypt(password))
//                .build());

        return "test";
    }
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
    public UserResponse getDetailUser(String id) throws RuntimeException{
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
    @Override
    public UserResponse getDetailUserByUsername(String username) throws RuntimeException{
        Optional<UserModel> userOpt = userRepository.findByUsername(username);

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
    @Override
    public boolean updateUser(String id, UserUpdateRequest request) throws RuntimeException {
        Optional<UserModel> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User is not found");
        }
        UserModel user = userOpt.get();

        // Check if the username is already taken by another user
        Optional<UserModel> checkSameNameUser = userRepository.findByUsername(request.getUsername());
        if (checkSameNameUser.isPresent() && !checkSameNameUser.get().getId().equals(user.getId())) {
            throw new RuntimeException("Username sudah digunakan");
        }

        // Update fields if provided
        if (!request.getUsername().isEmpty()) {
            user.setUsername(request.getUsername());
        }
        if (!request.getDivisi().isEmpty()) {
            Optional<Divisi> divisiOpt = divisiRepository.findByNama(request.getDivisi());
            if (divisiOpt.isEmpty()) {
                throw new RuntimeException("Divisi tidak dapat ditemukan");
            }
            user.setDivisi(divisiOpt.get());
        }
        if (!request.getRole().isEmpty()) {
            user.setRole(Role.valueOf(request.getRole()));
        }

        // Update password if provided and not empty
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(request.getPassword());
            user.setPassword(encryptedPassword);
        }

        userRepository.save(user);
        return true;
    }

    //    @Override
//    public boolean updateUser(String id, UserUpdateRequest request) throws RuntimeException{
//        Optional<UserModel> userOpt = userRepository.findById(id);
//        if (userOpt.isEmpty()) throw new RuntimeException("User is not found");
//        UserModel user = userOpt.get();
//
//        Optional<UserModel> checkSameNameUser = userRepository.findByUsername(request.getUsername());
//
//        if (checkSameNameUser.isPresent() && !String.valueOf(checkSameNameUser.get().getId()).equals(id)) throw new RuntimeException("Username sudah digunakan");
//
//
//        if (!request.getUsername().isEmpty()) user.setUsername(request.getUsername());
//        if (!request.getDivisi().isEmpty()){
//            Optional<Divisi> divisiOpt = divisiRepository.findByNama(request.getDivisi());
//            if (divisiOpt.isEmpty()) throw new RuntimeException("Divisi tidak dapat ditemukan");
//            user.setDivisi(divisiOpt.get());
//        }
//        if (!request.getRole().isEmpty()) user.setRole(Role.valueOf(request.getRole()));
//        userRepository.save(user);
//        return true;
//    }
    @Override
    public boolean deleteUser(String id) throws RuntimeException{
        Optional<UserModel> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) throw new RuntimeException("User tidak ditemukan");
        UserModel user = userOpt.get();
        userRepository.delete(user);
        return true;
    }

    public UserModel getUserByUsername(String username)throws RuntimeException{
        Optional<UserModel> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) throw new RuntimeException("User not found");
        return userOpt.get();
    }

    public String encrypt(String password) {
        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    private String randomPass(){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }
}
