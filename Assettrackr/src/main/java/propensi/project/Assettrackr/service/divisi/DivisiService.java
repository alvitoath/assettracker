package propensi.project.Assettrackr.service.divisi;

import propensi.project.Assettrackr.model.Divisi;
import propensi.project.Assettrackr.model.Server;
import propensi.project.Assettrackr.model.dto.request.CreateUpdateDivisiRequest;

import java.util.List;

public interface DivisiService {
    public Divisi createDivisi(CreateUpdateDivisiRequest request) throws RuntimeException;
    public List<Divisi> getAllDivisi();

    public String deleteDivisi(String id)throws RuntimeException;
    public Divisi updateDivisi(String id, CreateUpdateDivisiRequest request) throws RuntimeException;
    public List<Server> getServerByDivisi(String nama) throws RuntimeException;
    public List<Server> getServerByDivisiId(String id) throws RuntimeException;
}

