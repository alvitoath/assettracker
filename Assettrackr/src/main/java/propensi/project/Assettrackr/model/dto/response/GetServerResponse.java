package propensi.project.Assettrackr.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import propensi.project.Assettrackr.model.Divisi;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetServerResponse {
    private String id;
    private String nama;
    private String ipAddress;
    private String divisi;
    private String status;
    private String environment;
}
