package propensi.project.Assettrackr.service.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.project.Assettrackr.model.*;
import propensi.project.Assettrackr.model.dto.request.CreateUpdateServerRequest;
import propensi.project.Assettrackr.model.dto.response.GetServerResponse;
import propensi.project.Assettrackr.model.dto.response.ServerDetailResponse;
import propensi.project.Assettrackr.model.dto.request.ServerUpdateRequest;
import propensi.project.Assettrackr.repository.DivisiRepository;
import propensi.project.Assettrackr.repository.ServerRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
    public Server createServer(CreateUpdateServerRequest request) throws RuntimeException {

        Optional<Divisi> divisiOpt = divisiRepository.findByNama(request.getDivisi());
        if (divisiOpt.isEmpty()) {
            throw new RuntimeException("Divisi tidak tersedia");
        }

        Divisi divisi = divisiOpt.get();

        Status status;
        try {
            status = Status.valueOf(request.getStatus());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Status tidak valid. Pilih antara AKTIF, MAINTENANCE, atau NON-AKTIF.");
        }

        return repository.save(Server.builder()
                .nama(request.getNama())
                .ipAddress(request.getIpAddress())
                .divisi(divisi)
                .lokasi(request.getLokasi())
                .status(status)
                .sistemOperasi(request.getSistemOperasi())
                .bahasaPemrograman(request.getBahasaPemrograman())
                .versiBahasa(request.getVersiBahasa())
                .framework(request.getFramework())
                .versiFramework(request.getVersiFramework())
                .environment(ServerEnvironment.valueOf(request.getEnvironment()))
                .listServerChanges(new ArrayList<>())
                .build());
    }
    @Override
    public List<GetServerResponse> getServerDivisi(String namaDivisi) throws RuntimeException{
        Optional<Divisi> divisiOpt = divisiRepository.findByNama(namaDivisi);
        if (divisiOpt.isEmpty()) throw new RuntimeException("Divisi tidak ditemukan");

        Divisi divisi = divisiOpt.get();
        List<Server> lstServer = divisi.getListServer();

        return lstServer.stream().map(server -> new GetServerResponse(String.valueOf(server.getId()), server.getNama(), server.getIpAddress(), divisi.getNama(), String.valueOf(server.getStatus()), String.valueOf(server.getEnvironment()))).collect(Collectors.toList());
    }
    @Override
    public String deleteServer(String id) throws RuntimeException{
        Optional<Server> serverOpt = repository.findById(id);
        if (serverOpt.isEmpty()) throw new RuntimeException("Server tidak ditemukan");

        Server server = serverOpt.get();
        repository.delete(server);

        return "Success";
    }
    @Override
    public Boolean updateServer(String id, ServerUpdateRequest request) throws RuntimeException{
        Optional<Server> serverOpt = repository.findById(id);
        if (serverOpt.isEmpty()) throw new RuntimeException("Server is not found");
        Server server = serverOpt.get();

        if (!request.getNama().isEmpty()) server.setNama(request.getNama());
        if (!request.getDivisi().isEmpty()){
            Optional<Divisi> divisiOpt = divisiRepository.findByNama(request.getDivisi());
            if (divisiOpt.isEmpty()) throw new RuntimeException("Divisi tidak dapat ditemukan");
            server.setDivisi(divisiOpt.get());
        }
        if (!request.getIpAddress().isEmpty()) server.setIpAddress(request.getIpAddress());
        if (!request.getStatus().isEmpty()) server.setStatus(Status.valueOf(request.getStatus()));
        if (!request.getSistemOperasi().isEmpty()) server.setSistemOperasi(request.getSistemOperasi());
        if (!request.getBahasaPemrograman().isEmpty()) server.setBahasaPemrograman(request.getBahasaPemrograman());
        if (!request.getLokasi().isEmpty()) server.setLokasi(request.getLokasi());
        if (!request.getVersiBahasa().isEmpty()) server.setVersiBahasa(request.getVersiBahasa());
        if (!request.getFramework().isEmpty()) server.setFramework(request.getFramework());
        if (!request.getVersiFramework().isEmpty()) server.setVersiFramework(request.getVersiFramework());
        if (!request.getEnvironment().isEmpty()) server.setEnvironment(ServerEnvironment.valueOf(request.getEnvironment()));
        repository.save(server);
        return true;
    }

    @Override
    public ServerDetailResponse getServerDetail(String id) throws RuntimeException{
        Optional<Server> serverOpt = repository.findById(id);
        if (serverOpt.isEmpty()) throw new RuntimeException("Server is not found");
        Server server = serverOpt.get();

        return new ServerDetailResponse(String.valueOf(server.getId()), server.getNama(), server.getIpAddress(),
                server.getDivisi().getNama(), server.getLokasi(), server.getStatus().toString(), server.getSistemOperasi(),
                server.getBahasaPemrograman(), server.getVersiBahasa(), server.getFramework(), server.getVersiFramework(), server.getEnvironment().toString());
    }

    @Override
    public List<GetServerResponse> getAllServer() throws RuntimeException{
        List<Server> lstServer = repository.findAll();
        return lstServer.stream().map(server -> new GetServerResponse(String.valueOf(server.getId()), server.getNama(), server.getIpAddress(), server.getDivisi().getNama(), String.valueOf(server.getStatus()), String.valueOf(server.getEnvironment()))).collect(Collectors.toList());
    }
}
