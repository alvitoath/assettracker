package propensi.project.Assettrackr.service.server;

import propensi.project.Assettrackr.model.Server;
import propensi.project.Assettrackr.model.dto.CreateUpdateServerRequest;
import propensi.project.Assettrackr.model.dto.GetServerResponse;
import propensi.project.Assettrackr.model.dto.ServerDetailResponse;
import propensi.project.Assettrackr.model.dto.ServerUpdateRequest;

import java.util.List;

public interface ServerService {

    public Server createServer(CreateUpdateServerRequest request) throws RuntimeException;
    public List<GetServerResponse> getServerDivisi(String namaDivisi) throws RuntimeException;
    public String deleteServer(Integer id) throws RuntimeException;
    public boolean updateServer(Integer id, ServerUpdateRequest request) throws RuntimeException;
    public ServerDetailResponse getServerDetail(Integer id) throws RuntimeException;
    public List<GetServerResponse> getAllServer();
}
