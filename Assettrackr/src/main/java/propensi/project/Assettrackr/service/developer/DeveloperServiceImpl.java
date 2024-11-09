package propensi.project.Assettrackr.service.developer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.project.Assettrackr.model.Developer;
import propensi.project.Assettrackr.model.DeveloperStatus;
import propensi.project.Assettrackr.model.dto.request.CreateDeveloperRequest;
import propensi.project.Assettrackr.model.dto.response.DeveloperResponse;
import propensi.project.Assettrackr.repository.DeveloperRepository;

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

        return mapper(developer);
    }

    private DeveloperResponse mapper(Developer developer){
        return new DeveloperResponse(developer.getId(), developer.getNama(), developer.getKeahlian(), developer.getStatus().toString());
    }
}
