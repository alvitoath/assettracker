package propensi.project.Assettrackr.service.changes;

import propensi.project.Assettrackr.model.dto.CreateChangesRequest;
import propensi.project.Assettrackr.model.dto.ServerChangesResponse;

public interface ServerChangesService {

    public ServerChangesResponse createServerChanges(CreateChangesRequest request) throws Exception;
}
