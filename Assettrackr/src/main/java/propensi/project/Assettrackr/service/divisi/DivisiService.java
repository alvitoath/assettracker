package propensi.project.Assettrackr.service.divisi;

import propensi.project.Assettrackr.model.Divisi;
import propensi.project.Assettrackr.model.dto.CreateUpdateDivisiRequest;

import java.util.List;

public interface DivisiService {
    public Divisi createDivisi(CreateUpdateDivisiRequest request) throws RuntimeException;
    public List<Divisi> getAllDivisi();

    public String deleteDivisi(Integer id)throws RuntimeException;

}

