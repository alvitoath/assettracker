package propensi.project.Assettrackr.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeveloperResponse {
    private String id;
    private String nama;
    private String keahlian;
    private String status;
}
