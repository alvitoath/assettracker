package propensi.project.Assettrackr.service.user;

import propensi.project.Assettrackr.model.dto.*;

public interface UserService {


    public String login(LoginRequest request) throws RuntimeException;
    public ListUserResponse getAllUser();
    public UserResponse getDetailUser(Integer id) throws RuntimeException;

}
