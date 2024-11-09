package propensi.project.Assettrackr.service.server;

import propensi.project.Assettrackr.model.Server;
import propensi.project.Assettrackr.model.dto.request.CreateUpdateServerRequest;
import propensi.project.Assettrackr.model.dto.response.GetServerResponse;
import propensi.project.Assettrackr.model.dto.response.ServerDetailResponse;
import propensi.project.Assettrackr.model.dto.request.ServerUpdateRequest;

import java.util.List;

public interface ServerService {

    public Server createServer(CreateUpdateServerRequest request) throws RuntimeException;
    public List<GetServerResponse> getServerDivisi(String namaDivisi) throws RuntimeException;
    public String deleteServer(String id) throws RuntimeException;
    public Boolean updateServer(String id, ServerUpdateRequest request) throws RuntimeException;
    public ServerDetailResponse getServerDetail(String id) throws RuntimeException;
    public List<GetServerResponse> getAllServer();
}
