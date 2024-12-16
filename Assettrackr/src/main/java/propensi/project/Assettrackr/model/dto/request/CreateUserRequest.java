package propensi.project.Assettrackr.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserRequest {
    //    private String nama;
//    private String username;
//    private String role;
//    private String divisi;
//    private String email;
    @NotBlank(message = "Nama harus diisi")
    private String nama;

    @NotBlank(message = "Username harus diisi")
    private String username;

    @NotBlank(message = "Role harus diisi")
    private String role;

    @NotBlank(message = "Divisi harus diisi")
    private String divisi;

    @NotBlank(message = "Email harus diisi")
    @Email(message = "Email tidak valid")
    private String email;

    @NotBlank(message = "Password harus diisi")
    private String password;

    // Getters and Setters
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
