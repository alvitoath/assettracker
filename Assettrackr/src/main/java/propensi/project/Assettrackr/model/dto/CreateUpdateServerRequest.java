package propensi.project.Assettrackr.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import propensi.project.Assettrackr.model.Status;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUpdateServerRequest {
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
}
