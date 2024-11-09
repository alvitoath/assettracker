package propensi.project.Assettrackr.service.solution;

import propensi.project.Assettrackr.model.dto.request.ChangesSolutionRequest;
import propensi.project.Assettrackr.model.dto.response.ChangesSolutionResponse;

public interface SolutionService {
    ChangesSolutionResponse createSolution(String id, ChangesSolutionRequest request);
    ChangesSolutionResponse updateSolution(String id, ChangesSolutionRequest request);
}
