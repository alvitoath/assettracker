package propensi.project.Assettrackr.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FinishedChangesResponse {
    private String id;
    private String serverId;
    private String serverName;
    private String tipePerbaikan;
    private String detailPerbaikan;
    private String tanggalDibuat;
    private String status;
    private String tanggalSelesai;
    private String divisi;
    private String solutionId;
    private String solution;
    private String solutionStatus;
    private String assignStatus;
    private List<DeveloperResponse> developers;
}
