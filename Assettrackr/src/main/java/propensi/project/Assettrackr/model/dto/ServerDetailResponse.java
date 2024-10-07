package propensi.project.Assettrackr.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServerDetailResponse {
    private String id;
    private String nama;
    private String ipAddr;
    private String divisi;
}
