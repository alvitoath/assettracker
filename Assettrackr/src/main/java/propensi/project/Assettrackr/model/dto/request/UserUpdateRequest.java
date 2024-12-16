package propensi.project.Assettrackr.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    @NotBlank(message = "Nama harus diisi")
    private String nama;

    @NotBlank(message = "Username harus diisi")
    private String username;

    @NotBlank(message = "Role harus diisi")
    private String role;

    @NotBlank(message = "Divisi harus diisi")
    private String divisi;

    @Email(message = "Email tidak valid")
    @NotBlank(message = "Email harus diisi")
    private String email;

    /**
     * Password is optional during an update.
     * If provided, it will be updated; otherwise, it remains unchanged.
     */
    private String password;
}
