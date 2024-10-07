package propensi.project.Assettrackr.service.divisi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.project.Assettrackr.model.Divisi;
import propensi.project.Assettrackr.model.dto.CreateUpdateDivisiRequest;
import propensi.project.Assettrackr.repository.DivisiRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DivisiServiceImpl implements DivisiService{
    @Autowired
    private DivisiRepository divisiRepository;
    @Override
    public Divisi createDivisi(CreateUpdateDivisiRequest request) throws RuntimeException{
        Optional<Divisi> divisiOpt = divisiRepository.findByNama(request.getNama());
        if (divisiOpt.isPresent()) throw new RuntimeException("Divisi sudah tersedia");

        return divisiRepository.save(Divisi.builder()
                .nama(request.getNama())
                .keterangan(request.getKeterangan())
                .build());
    }
    @Override
    public List<Divisi> getAllDivisi(){
        return divisiRepository.findAll();
    }
    @Override
    public String deleteDivisi(Integer id)throws RuntimeException{
        Optional<Divisi> divisiOpt = divisiRepository.findById(id);
        if (divisiOpt.isEmpty()) throw new RuntimeException("Divisi tidak ditemukan");

        divisiRepository.delete(divisiOpt.get());
        return "Success";
    }

}
