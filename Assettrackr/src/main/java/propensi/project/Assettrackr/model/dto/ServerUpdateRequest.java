package propensi.project.Assettrackr.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServerUpdateRequest {
    private String nama;
    private String ipAddr;
    private String divisi;
}
