package propensi.project.Assettrackr.service.changes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import propensi.project.Assettrackr.model.Server;
import propensi.project.Assettrackr.model.ServerChanges;
import propensi.project.Assettrackr.model.dto.request.CreateChangesRequest;
import propensi.project.Assettrackr.model.dto.request.UpdateChangesRequest;
import propensi.project.Assettrackr.model.dto.response.ServerChangesResponse;
import propensi.project.Assettrackr.repository.ServerChangesRepository;
import propensi.project.Assettrackr.repository.ServerRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

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

        return mapper(response);
    }
    @Override
    public ServerChangesResponse getServerChangesDetail(String id){
        try {
            UUID uuid = UUID.fromString(id);
            ServerChanges serverChanges = repository.getReferenceById(uuid);
            return mapper(serverChanges);
        } catch (Exception e){
            throw new RuntimeException("Id is wrong");
        }
    }

    @Override
    public ServerChangesResponse updateServerChanges(String id, UpdateChangesRequest request){
        try {
            UUID uuid = UUID.fromString(id);
            ServerChanges serverChanges = repository.getReferenceById(uuid);

            if (!request.getNotes().isEmpty()) serverChanges.setNotes(request.getNotes());
            if (!request.getProblem().isEmpty()) serverChanges.setProblem(request.getProblem());
            if (!request.getStatus().isEmpty()) serverChanges.setStatus(request.getStatus());

            ServerChanges response = repository.save(serverChanges);
            return mapper(response);
        } catch (Exception e){
            throw new RuntimeException("Id is wrong");
        }
    }

    private ServerChangesResponse mapper(ServerChanges serverChanges){
        return new ServerChangesResponse(
                serverChanges.getId().toString(),
                serverChanges.getServer().getId().toString(),
                serverChanges.getProblem(), serverChanges.getNotes(),
                serverChanges.getCreatedAt().toString(),
                serverChanges.getUpdatedAt().toString(),
                serverChanges.getStatus());
    }
}
