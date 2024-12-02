package propensi.project.Assettrackr.service.user;

import propensi.project.Assettrackr.model.UserModel;
import propensi.project.Assettrackr.model.dto.request.CreateUserRequest;
import propensi.project.Assettrackr.model.dto.request.LoginRequest;
import propensi.project.Assettrackr.model.dto.request.UserUpdateRequest;
import propensi.project.Assettrackr.model.dto.response.ListUserResponse;
import propensi.project.Assettrackr.model.dto.response.UserResponse;

public interface UserService {

    public String createUser(CreateUserRequest request) throws RuntimeException;
    public String login(LoginRequest request) throws RuntimeException;
    public ListUserResponse getAllUser();
    public UserResponse getDetailUser(String id) throws RuntimeException;
    public boolean updateUser(String id, UserUpdateRequest request) throws RuntimeException;
    public boolean deleteUser(String id) throws RuntimeException;
    public UserResponse getDetailUserByUsername(String username) throws RuntimeException;
    public UserModel getUserByUsername(String username)throws RuntimeException;
}
