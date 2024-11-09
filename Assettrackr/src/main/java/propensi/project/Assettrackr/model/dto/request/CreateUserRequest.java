package propensi.project.Assettrackr.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserRequest {
    private String username;
    private String nama;
    private String email;
    private String role;
    private String divisi;
}
