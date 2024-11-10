package propensi.project.Assettrackr.service.divisi;

import org.springframework.web.multipart.MultipartFile;
import propensi.project.Assettrackr.model.Divisi;
import propensi.project.Assettrackr.model.Server;
import propensi.project.Assettrackr.model.dto.request.CreateUpdateDivisiRequest;

import java.util.List;

public interface DivisiService {
    public Divisi createDivisi(CreateUpdateDivisiRequest request, MultipartFile image) throws RuntimeException;
    public List<Divisi> getAllDivisi();

    public String deleteDivisi(String id)throws RuntimeException;
    public Divisi updateDivisi(String id, CreateUpdateDivisiRequest request, MultipartFile image) throws RuntimeException;
    public List<Server> getServerByDivisi(String nama) throws RuntimeException;
    public List<Server> getServerByDivisiId(String id) throws RuntimeException;
    public Divisi getDivisiById(String id) throws RuntimeException;
}

