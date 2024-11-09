package propensi.project.Assettrackr.service.changes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import propensi.project.Assettrackr.model.Server;
import propensi.project.Assettrackr.model.ServerChanges;
import propensi.project.Assettrackr.model.ServerChangesStatus;
import propensi.project.Assettrackr.model.dto.request.CreateChangesRequest;
import propensi.project.Assettrackr.model.dto.request.UpdateChangesRequest;
import propensi.project.Assettrackr.model.dto.response.ServerChangesResponse;
import propensi.project.Assettrackr.repository.ServerChangesRepository;
import propensi.project.Assettrackr.repository.ServerRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
                .tipePerbaikan(request.getTipePerbaikan())
                .detailPerbaikan(request.getDetailPerbaikan())
                .tanggalDibuat(new Date())
                .status(ServerChangesStatus.valueOf(request.getStatus()))
                .build());

        server.getListServerChanges().add(response);
        serverRepository.save(server);

        return mapper(response);
    }
    @Override
    public ServerChangesResponse getServerChangesDetail(String id){
        try {
            ServerChanges serverChanges = repository.getReferenceById(id);
            return mapper(serverChanges);
        } catch (Exception e){
            throw new RuntimeException("Id is wrong");
        }
    }

    @Override
    public ServerChangesResponse updateServerChanges(String id, UpdateChangesRequest request){
        try {
            ServerChanges serverChanges = repository.getReferenceById(id);

            if (!request.getTipePerbaikan().isEmpty()) serverChanges.setTipePerbaikan(request.getTipePerbaikan());
            if (!request.getDetailPerbaikan().isEmpty()) serverChanges.setDetailPerbaikan(request.getDetailPerbaikan());
            if (!request.getStatus().isEmpty()) serverChanges.setStatus(ServerChangesStatus.valueOf(request.getStatus()));

            ServerChanges response = repository.save(serverChanges);
            return mapper(response);
        } catch (Exception e){
            throw new RuntimeException("Id is wrong");
        }
    }


    public String deleteServer(String id) throws RuntimeException, EntityNotFoundException {
        try {
            //cek status
            ServerChanges serverChanges = repository.getReferenceById(id);
            repository.delete(serverChanges);

            return "Perubahan telah dihapus";
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ServerChangesResponse> getServerChangesByDivisi(String divisi) {
        List<ServerChanges> serverChanges = repository.findServerChangesByDivisi(divisi);
        return serverChanges.stream().map(this::mapper).collect(Collectors.toList());
    }

    private ServerChangesResponse mapper(ServerChanges serverChanges){
        return new ServerChangesResponse(
                serverChanges.getId().toString(),
                serverChanges.getServer().getId().toString(),
                serverChanges.getTipePerbaikan(),
                serverChanges.getDetailPerbaikan(),
                serverChanges.getTanggalDibuat().toString(),
                serverChanges.getStatus().toString());
    }
}
