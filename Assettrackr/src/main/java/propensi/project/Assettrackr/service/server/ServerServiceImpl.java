package propensi.project.Assettrackr.service.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.project.Assettrackr.model.Divisi;
import propensi.project.Assettrackr.model.Server;
import propensi.project.Assettrackr.model.dto.CreateUpdateServerRequest;
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

}
