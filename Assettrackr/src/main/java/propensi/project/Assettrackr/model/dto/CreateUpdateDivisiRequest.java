package propensi.project.Assettrackr.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUpdateDivisiRequest {
    private String nama;
    private String keterangan;
}
