package propensi.project.Assettrackr.service.user;

import propensi.project.Assettrackr.model.dto.*;

public interface UserService {

    public String createUser(CreateUserRequest request) throws RuntimeException;
    public String login(LoginRequest request) throws RuntimeException;
    public ListUserResponse getAllUser();
    public UserResponse getDetailUser(Integer id) throws RuntimeException;
    public boolean updateUser(Integer id, UserUpdateRequest request) throws RuntimeException;
    public boolean deleteUser(Integer id) throws RuntimeException;
    public UserResponse getDetailUserByUsername(String username) throws RuntimeException;
}
