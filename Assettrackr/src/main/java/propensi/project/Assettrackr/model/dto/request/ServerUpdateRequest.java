package propensi.project.Assettrackr.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServerUpdateRequest {
    private String nama;
    private String ipAddress;
    private String divisi;
    private String lokasi;
    private String status;
    private String sistemOperasi;
    private String bahasaPemrograman;
    private String versiBahasa;
    private String framework;
    private String versiFramework;
    private String environment;
}
