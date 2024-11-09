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
    private String tipePerbaikan;
    private String detailPerbaikan;
    private String tanggalDibuat;
    private String status;

}
