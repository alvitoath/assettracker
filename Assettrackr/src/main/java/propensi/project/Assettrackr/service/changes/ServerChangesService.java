package propensi.project.Assettrackr.service.changes;

import propensi.project.Assettrackr.model.dto.request.CreateChangesRequest;
import propensi.project.Assettrackr.model.dto.request.UpdateChangesRequest;
import propensi.project.Assettrackr.model.dto.response.ServerChangesResponse;

public interface ServerChangesService {

    public ServerChangesResponse createServerChanges(CreateChangesRequest request) throws Exception;
    public ServerChangesResponse getServerChangesDetail(String id) ;
    public ServerChangesResponse updateServerChanges(String id, UpdateChangesRequest request);
    public String deleteServer(String id) throws RuntimeException;
}
