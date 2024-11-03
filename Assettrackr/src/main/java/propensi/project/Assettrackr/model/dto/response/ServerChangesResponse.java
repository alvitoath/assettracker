package propensi.project.Assettrackr.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServerChangesResponse {
    private String id;
    private String serverId;
    private String problem;
    private String notes;
    private String createdAt;
    private String updatedAt;
    private String status;

}
