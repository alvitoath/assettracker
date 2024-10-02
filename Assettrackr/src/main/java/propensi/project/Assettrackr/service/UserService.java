package propensi.project.Assettrackr.service;

import propensi.project.Assettrackr.model.UserModel;

public interface UserService {
    UserModel getUserById(String id);
    String getUsername();
    UserModel getUserByUsername(String username);

    Boolean checkUsernameRegistered(String username);
    Boolean checkEmailRegistered(String email);
    public String encrypt(String password);
}
