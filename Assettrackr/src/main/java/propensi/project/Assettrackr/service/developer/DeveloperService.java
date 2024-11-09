package propensi.project.Assettrackr.service.developer;

import propensi.project.Assettrackr.model.dto.request.CreateDeveloperRequest;
import propensi.project.Assettrackr.model.dto.request.UpdateDeveloperRequest;
import propensi.project.Assettrackr.model.dto.response.DeveloperResponse;

import java.util.List;

public interface DeveloperService {
    DeveloperResponse createDeveloper(CreateDeveloperRequest request);
    DeveloperResponse updateDeveloper(UpdateDeveloperRequest request, String id) throws Exception;
    DeveloperResponse getDetailDeveloper(String id) throws Exception;
    List<DeveloperResponse> getAllDeveloper();
}
