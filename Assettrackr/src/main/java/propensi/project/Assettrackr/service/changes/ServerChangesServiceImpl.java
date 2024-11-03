package propensi.project.Assettrackr.service.changes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.project.Assettrackr.model.Server;
import propensi.project.Assettrackr.model.ServerChanges;
import propensi.project.Assettrackr.model.dto.request.CreateChangesRequest;
import propensi.project.Assettrackr.model.dto.response.ServerChangesResponse;
import propensi.project.Assettrackr.repository.ServerChangesRepository;
import propensi.project.Assettrackr.repository.ServerRepository;

import java.util.Date;

@Service
public class ServerChangesServiceImpl implements ServerChangesService{
    @Autowired
    private ServerChangesRepository repository;

    @Autowired
    private ServerRepository serverRepository;

    @Override
    public ServerChangesResponse createServerChanges(CreateChangesRequest request) throws Exception{
        Server server = serverRepository.getReferenceById(request.getServerId());
        ServerChanges response = repository.save(ServerChanges.builder()
                .server(server)
                .problem(request.getProblem())
                .notes(request.getNotes())
                .createdAt(new Date())
                .updatedAt(new Date())
                .status(request.getStatus())
                .build());
        return new ServerChangesResponse(
                response.getId().toString(),
                response.getServer().getId().toString(),
                response.getProblem(), response.getNotes(),
                response.getCreatedAt().toString(),
                response.getUpdatedAt().toString(),
                response.getStatus());
    }
}
