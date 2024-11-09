package propensi.project.Assettrackr.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateDeveloperRequest {
        private String nama;
        private String keahlian;
        private String status;
}
