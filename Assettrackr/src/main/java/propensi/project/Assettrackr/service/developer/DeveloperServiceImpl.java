package propensi.project.Assettrackr.service.developer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import propensi.project.Assettrackr.model.Developer;
import propensi.project.Assettrackr.model.DeveloperStatus;
import propensi.project.Assettrackr.model.dto.request.CreateDeveloperRequest;
import propensi.project.Assettrackr.model.dto.request.UpdateDeveloperRequest;
import propensi.project.Assettrackr.model.dto.response.DeveloperResponse;
import propensi.project.Assettrackr.repository.DeveloperRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeveloperServiceImpl implements DeveloperService{
    @Autowired
    DeveloperRepository repository;

    @Override
    public DeveloperResponse createDeveloper(CreateDeveloperRequest request) {
        Developer developer = Developer.builder()
                .nama(request.getNama())
                .keahlian(request.getKeahlian())
                .status(DeveloperStatus.Available).build();
        repository.save(developer);

        return mapper(developer);
    }

    @Override
    public DeveloperResponse updateDeveloper(UpdateDeveloperRequest request, String id) throws Exception {
        Developer developer = repository.getReferenceById(id);
        if (!request.getKeahlian().isEmpty()) developer.setNama(request.getNama());
        if (!request.getNama().isEmpty()) developer.setKeahlian(request.getKeahlian());
        if (!request.getStatus().isEmpty()) developer.setStatus(DeveloperStatus.valueOf(request.getStatus()));
        Developer response = repository.save(developer);
        return mapper(response);
    }

    @Override
    public DeveloperResponse getDetailDeveloper(String id) throws Exception {
        Developer developer = repository.getReferenceById(id);
        return mapper(developer);
    }

    @Override
    public List<DeveloperResponse> getAllDeveloper() {
        List<Developer> response = repository.findAll();
        return response.stream().map(this::mapper).collect(Collectors.toList());
    }

    @Override
    public Boolean deleteDeveloper(String id) {
        Developer developer = repository.getReferenceById(id);
        repository.delete(developer);
        return true;
    }

    private DeveloperResponse mapper(Developer developer){
        return new DeveloperResponse(
                developer.getId(),
                developer.getNama(),
                developer.getKeahlian(),
                developer.getStatus().toString());
    }
}
