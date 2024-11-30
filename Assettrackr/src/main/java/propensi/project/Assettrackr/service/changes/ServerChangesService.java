package propensi.project.Assettrackr.service.changes;

import propensi.project.Assettrackr.model.dto.request.ChangesSolutionRequest;
import propensi.project.Assettrackr.model.dto.request.CreateChangesRequest;
import propensi.project.Assettrackr.model.dto.request.UpdateChangesRequest;
import propensi.project.Assettrackr.model.dto.response.FinishedChangesResponse;
import propensi.project.Assettrackr.model.dto.response.ServerChangesResponse;

import java.util.List;

public interface ServerChangesService {

    public ServerChangesResponse createServerChanges(CreateChangesRequest request) throws Exception;
    public ServerChangesResponse getServerChangesDetail(String id) ;
    public ServerChangesResponse updateServerChanges(String id, UpdateChangesRequest request);
    public Boolean deleteServer(String id) throws RuntimeException;
    public List<ServerChangesResponse> getServerChangesByDivisi(String divisi);
    public List<ServerChangesResponse> getServerChangesByDivisiName(String divisiName);
    List<ServerChangesResponse> getAll();
    public Boolean rejectServerChanges(String id);
    public List<FinishedChangesResponse> getAllFinishByDivisiId(String id);
    public List<FinishedChangesResponse> getAllSolvedSolution();

}
