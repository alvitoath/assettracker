package propensi.project.Assettrackr.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateChangesRequest {
    private String tipePerbaikan;
    private String detailPerbaikan;
    private String status;
}
