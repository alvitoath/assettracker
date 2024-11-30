package propensi.project.Assettrackr.service.changes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import propensi.project.Assettrackr.model.*;
import propensi.project.Assettrackr.model.dto.request.ChangesSolutionRequest;
import propensi.project.Assettrackr.model.dto.request.CreateChangesRequest;
import propensi.project.Assettrackr.model.dto.request.UpdateChangesRequest;
import propensi.project.Assettrackr.model.dto.response.DeveloperResponse;
import propensi.project.Assettrackr.model.dto.response.FinishedChangesResponse;
import propensi.project.Assettrackr.model.dto.response.ServerChangesResponse;
import propensi.project.Assettrackr.repository.DeveloperRepository;
import propensi.project.Assettrackr.repository.ServerChangesRepository;
import propensi.project.Assettrackr.repository.ServerRepository;
import propensi.project.Assettrackr.repository.SolutionRepository;

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

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private DeveloperRepository developerRepository;

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

        if (ServerChangesStatus.valueOf(request.getStatus()).equals(ServerChangesStatus.Unsolved)){
            ChangesSolution solution = solutionRepository.save(ChangesSolution.builder()
                    .status(SolutionStatus.Unsolved)
                    .serverChanges(response)
                    .build());
            response.setSolution(solution);
            repository.save(response);
        }

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

            if (ServerChangesStatus.valueOf(request.getStatus()).equals(ServerChangesStatus.Unsolved) && response.getSolution() == null){
                ChangesSolution solution = solutionRepository.save(ChangesSolution.builder()
                        .status(SolutionStatus.Unsolved)
                        .serverChanges(response)
                        .build());
                response.setSolution(solution);
                repository.save(response);
            }

            return mapper(response);
        } catch (Exception e){
            throw new RuntimeException("Id is wrong");
        }
    }

    @Override
    public Boolean deleteServer(String id) throws RuntimeException, EntityNotFoundException {
        try {
            ServerChanges serverChanges = repository.getReferenceById(id);
            if (serverChanges.getStatus().equals(ServerChangesStatus.Draft)){
                repository.delete(serverChanges);
                return true;
            }

            if (serverChanges.getStatus().equals(ServerChangesStatus.Ditolak)){
                ChangesSolution solution = serverChanges.getSolution();
                serverChanges.setSolution(null);

                repository.save(serverChanges);

                solutionRepository.delete(solution);
                repository.delete(serverChanges);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ServerChangesResponse> getServerChangesByDivisi(String divisi) {
        List<ServerChanges> serverChanges = repository.findServerChangesByDivisi(divisi);
        return serverChanges.stream().map(this::mapper).collect(Collectors.toList());
    }

    @Override
    public List<ServerChangesResponse> getServerChangesByDivisiName(String divisiName) {
        List<ServerChanges> serverChanges = repository.findServerChangesByDivisiName(divisiName);
        return serverChanges.stream().map(this::mapper).collect(Collectors.toList());
    }

    @Override
    public List<ServerChangesResponse> getAll() {
        List<ServerChanges> response = repository.findAll();
        return response.stream().map(this::mapper).collect(Collectors.toList());
    }

    @Override
    public Boolean rejectServerChanges(String id) {
        ServerChanges serverChanges = repository.getReferenceById(id);
        if (serverChanges.getSolution().getStatus() != null && serverChanges.getSolution().getStatus().equals(SolutionStatus.Solved)) throw new RuntimeException("Perubahan tidak dapat ditolak");

        serverChanges.setStatus(ServerChangesStatus.Ditolak);
        repository.save(serverChanges);
        return true;
    }
    @Override
    public List<FinishedChangesResponse> getAllFinishByDivisiId(String id){
        List<ServerChanges> response = repository.findserverChangesFinishedByDivisiId(id);
        return response.stream().map(this::finishedMapper).collect(Collectors.toList());
    }
    @Override
    public List<FinishedChangesResponse> getAllSolvedSolution(){
        List<ServerChanges> response = repository.findFinishedChangesSolution();
        return response.stream().map(this::finishedMapper).collect(Collectors.toList());
    }

    public FinishedChangesResponse assingDeveloper(String changesId, String developerId)throws EntityNotFoundException, RuntimeException{
        ServerChanges serverChanges = repository.getReferenceById(changesId);
        Developer developer = developerRepository.getReferenceById(developerId);

        if (serverChanges.getSolution().getStatus()!= SolutionStatus.Solved) throw new RuntimeException("Solusi belum dibuat");
        if (developer.getStatus().equals(DeveloperStatus.Unavailable)) throw new RuntimeException("Developer sedang tidak tersedia");

        developer.setStatus(DeveloperStatus.Unavailable);
        serverChanges.setDeveloper(developer);
        return finishedMapper(repository.save(serverChanges));

    }
    private ServerChangesResponse mapper(ServerChanges serverChanges){
        ServerChangesResponse response = ServerChangesResponse.builder()
                .id(serverChanges.getId())
                .serverId(serverChanges.getServer().getId())
                .serverName(serverChanges.getServer().getNama())
                .tipePerbaikan(serverChanges.getTipePerbaikan())
                .detailPerbaikan(serverChanges.getDetailPerbaikan())
                .tanggalDibuat(serverChanges.getTanggalDibuat().toString())
                .status(serverChanges.getStatus().toString())
                .divisi(serverChanges.getServer().getDivisi().getNama())
                .build();

        if (serverChanges.getTanggalSelesai() != null) response.setTanggalSelesai(serverChanges.getTanggalSelesai().toString());
        if (serverChanges.getSolution() != null) {
            response.setSolutionId(serverChanges.getSolution().getId());
            if (serverChanges.getStatus().toString().equals("Ditolak")) {
                response.setSolutionStatus("Ditolak");
            } else if (serverChanges.getStatus().toString().equals("Draft")) {
                response.setSolutionStatus("Unsolved");
            } else {
                response.setSolutionStatus(serverChanges.getSolution().getStatus().toString());
            }
        }
        return response;
    }

    private FinishedChangesResponse finishedMapper(ServerChanges serverChanges){
        FinishedChangesResponse response = FinishedChangesResponse.builder()
                .id(serverChanges.getId())
                .serverId(serverChanges.getServer().getId())
                .serverName(serverChanges.getServer().getNama())
                .tipePerbaikan(serverChanges.getTipePerbaikan())
                .detailPerbaikan(serverChanges.getDetailPerbaikan())
                .tanggalDibuat(String.valueOf(serverChanges.getTanggalDibuat()))
                .tanggalSelesai(String.valueOf(serverChanges.getTanggalSelesai()))
                .status(serverChanges.getStatus().toString())
                .divisi(serverChanges.getServer().getDivisi().getNama())
                .solutionId(serverChanges.getSolution().getId())
                .solution(serverChanges.getSolution().getSolution())
                .solutionStatus(serverChanges.getSolution().getStatus().toString()).build();

        if (serverChanges.getDeveloper() != null){
            response.setDeveloperId(serverChanges.getDeveloper().getId());
            response.setDeveloperName(serverChanges.getDeveloper().getNama());
            response.setDeveloperStatus(serverChanges.getDeveloper().getStatus().toString());
        }
        return response;
    }
}
