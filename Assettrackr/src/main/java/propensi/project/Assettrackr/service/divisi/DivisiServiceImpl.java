package propensi.project.Assettrackr.service.divisi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.project.Assettrackr.model.Divisi;
import propensi.project.Assettrackr.model.Server;
import propensi.project.Assettrackr.model.dto.request.CreateUpdateDivisiRequest;
import propensi.project.Assettrackr.repository.DivisiRepository;
import propensi.project.Assettrackr.repository.ServerRepository;
import propensi.project.Assettrackr.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DivisiServiceImpl implements DivisiService{
    @Autowired
    private DivisiRepository divisiRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServerRepository serverRepository;
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
    public String deleteDivisi(Integer id) throws RuntimeException {
        Optional<Divisi> divisiOpt = divisiRepository.findById(id);
        if (divisiOpt.isEmpty()) throw new RuntimeException("Divisi tidak ditemukan");

        Divisi divisi = divisiOpt.get();

        long userCount = userRepository.countByDivisiId(id);
        long serverCount = serverRepository.countByDivisiId(id);

        if (userCount > 0 || serverCount > 0) {
            throw new RuntimeException("Tidak bisa menghapus divisi, masih terdapat Pengguna / Server yang terhubung");
        }

        divisiRepository.delete(divisi);
        return "Success";
    }

    @Override
    public Divisi updateDivisi(Integer id, CreateUpdateDivisiRequest request) throws RuntimeException{
        Optional<Divisi> divisiOpt = divisiRepository.findById(id);
        if (divisiOpt.isEmpty()) throw new RuntimeException("Divisi tidak ditemukan");
        Optional<Divisi> checkSameNameDivisi = divisiRepository.findByNama(request.getNama());
        if (checkSameNameDivisi.isPresent() && checkSameNameDivisi.get().getId() != id) throw new RuntimeException("Divisi sudah tersedia");
        Divisi divisi = divisiOpt.get();
        if (!request.getNama().isEmpty()) divisi.setNama(request.getNama());
        if (!request.getKeterangan().isEmpty()) divisi.setKeterangan(request.getKeterangan());

        return divisiRepository.save(divisi);
    }
    @Override
    public List<Server> getServerByDivisi(String nama) throws RuntimeException{
        Optional<Divisi> divisiOpt = divisiRepository.findByNama(nama);
        if (divisiOpt.isEmpty()) throw new RuntimeException("Divisi tidak ditemukan");

        Divisi divisi = divisiOpt.get();
        return divisi.getListServer();
    }

    @Override
    public List<Server> getServerByDivisiId(Integer id) throws RuntimeException{
        Optional<Divisi> divisiOpt = divisiRepository.findById(id);
        if (divisiOpt.isEmpty()) throw new RuntimeException("Divisi tidak ditemukan");

        Divisi divisi = divisiOpt.get();
        return divisi.getListServer();
    }
}
