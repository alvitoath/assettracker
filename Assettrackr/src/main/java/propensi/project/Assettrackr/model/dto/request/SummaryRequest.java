package propensi.project.Assettrackr.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SummaryRequest {
    private String start;
    private String end;
}
