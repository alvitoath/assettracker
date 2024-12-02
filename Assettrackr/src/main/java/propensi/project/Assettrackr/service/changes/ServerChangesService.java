package propensi.project.Assettrackr.service.changes;

import propensi.project.Assettrackr.model.dto.request.*;
import propensi.project.Assettrackr.model.dto.response.FinishedChangesResponse;
import propensi.project.Assettrackr.model.dto.response.ChartResponse;
import propensi.project.Assettrackr.model.dto.response.ServerChangesResponse;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface ServerChangesService {

    public ServerChangesResponse createServerChanges(CreateChangesRequest request , String token) throws Exception;
    public ServerChangesResponse getServerChangesDetail(String id) ;
    public ServerChangesResponse updateServerChanges(String id, UpdateChangesRequest request);
    public Boolean deleteServer(String id) throws RuntimeException;
    public List<ServerChangesResponse> getServerChangesByDivisi(String divisi);
    public List<ServerChangesResponse> getServerChangesByDivisiName(String divisiName);
    public List<FinishedChangesResponse> getAll();
    public Boolean rejectServerChanges(String id);
    public List<FinishedChangesResponse> getAllFinishByDivisiId(String id);
    public List<FinishedChangesResponse> getAllSolvedSolution();
    public FinishedChangesResponse assingDeveloper(String changesId, AssignUpdateDeveloperRequest request)throws EntityNotFoundException, RuntimeException;
    public FinishedChangesResponse updateDeveloper(String changesId, AssignUpdateDeveloperRequest request) throws RuntimeException;
    public FinishedChangesResponse finishChanges(String changesId) throws RuntimeException;
    public FinishedChangesResponse getFinishById(String changesId);
    public ChartResponse getLineGraphDailySummary(SummaryRequest request);
    public ChartResponse getLineGraphMonthlySummary(SummaryRequest request);
    public FinishedChangesResponse getResponseById(String changesId);
}
