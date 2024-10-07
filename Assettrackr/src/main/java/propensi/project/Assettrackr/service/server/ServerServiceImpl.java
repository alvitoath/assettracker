package propensi.project.Assettrackr.service.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.project.Assettrackr.model.Divisi;
import propensi.project.Assettrackr.model.Server;
import propensi.project.Assettrackr.model.dto.CreateUpdateServerRequest;
import propensi.project.Assettrackr.model.dto.GetServerResponse;
import propensi.project.Assettrackr.model.dto.ServerDetailResponse;
import propensi.project.Assettrackr.model.dto.ServerUpdateRequest;
import propensi.project.Assettrackr.repository.DivisiRepository;
import propensi.project.Assettrackr.repository.ServerRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@Transactional
public class ServerServiceImpl implements ServerService{

    @Autowired
    private ServerRepository repository;

    @Autowired
    private DivisiRepository divisiRepository;
    @Override
    public Server createServer(CreateUpdateServerRequest request) throws RuntimeException{
        Optional<Divisi> divisiOpt = divisiRepository.findByNama(request.getDivisi());
        if (divisiOpt.isEmpty()){
            throw new RuntimeException("Divisi tidak tersedia");
        }

        Divisi divisi = divisiOpt.get();

        return repository.save(Server.builder()
                .nama(request.getNama())
                .ipAddress(request.getIpAddress())
                .divisi(divisi).build());
    }
    @Override
    public List<GetServerResponse> getServerDivisi(String namaDivisi) throws RuntimeException{
        Optional<Divisi> divisiOpt = divisiRepository.findByNama(namaDivisi);
        if (divisiOpt.isEmpty()) throw new RuntimeException("Divisi tidak ditemukan");

        Divisi divisi = divisiOpt.get();
        List<Server> lstServer = divisi.getListServer();

        return lstServer.stream().map(server -> new GetServerResponse(String.valueOf(server.getId()), server.getNama(), server.getIpAddress())).collect(Collectors.toList());
    }
    @Override
    public String deleteServer(Integer id) throws RuntimeException{
        Optional<Server> serverOpt = repository.findById(id);
        if (serverOpt.isEmpty()) throw new RuntimeException("Server tidak ditemukan");

        Server server = serverOpt.get();
        repository.delete(server);

        return "Success";
    }
    @Override
    public boolean updateServer(Integer id, ServerUpdateRequest request) throws RuntimeException{
        Optional<Server> serverOpt = repository.findById(id);
        if (serverOpt.isEmpty()) throw new RuntimeException("Server is not found");
        Server server = serverOpt.get();

        if (!request.getNama().isEmpty()) server.setNama(request.getNama());
        if (!request.getDivisi().isEmpty()){
            Optional<Divisi> divisiOpt = divisiRepository.findByNama(request.getDivisi());
            if (divisiOpt.isEmpty()) throw new RuntimeException("Divisi tidak dapat ditemukan");
            server.setDivisi(divisiOpt.get());
        }
        if (!request.getIpAddr().isEmpty()) server.setIpAddress(request.getIpAddr());
        repository.save(server);
        return true;
    }

    @Override
    public ServerDetailResponse getServerDetail(Integer id) throws RuntimeException{
        Optional<Server> serverOpt = repository.findById(id);
        if (serverOpt.isEmpty()) throw new RuntimeException("Server is not found");
        Server server = serverOpt.get();

        return new ServerDetailResponse(String.valueOf(server.getId()), server.getNama(), server.getIpAddress(), server.getDivisi().getNama());
    }
}
