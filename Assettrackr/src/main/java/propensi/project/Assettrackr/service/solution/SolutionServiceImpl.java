package propensi.project.Assettrackr.service.solution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.project.Assettrackr.model.ChangesSolution;
import propensi.project.Assettrackr.model.ServerChanges;
import propensi.project.Assettrackr.model.dto.SolutionStatus;
import propensi.project.Assettrackr.model.dto.request.ChangesSolutionRequest;
import propensi.project.Assettrackr.model.dto.response.ChangesSolutionResponse;
import propensi.project.Assettrackr.repository.ServerChangesRepository;
import propensi.project.Assettrackr.repository.SolutionRepository;

@Service
public class SolutionServiceImpl implements SolutionService{
    @Autowired
    private SolutionRepository solutionRepository;
    @Autowired
    private ServerChangesRepository serverChangesRepository;

    @Override
    public ChangesSolutionResponse createSolution(String id, ChangesSolutionRequest request) {
        ServerChanges serverChanges = serverChangesRepository.getReferenceById(id);
        ChangesSolution changesSolution = solutionRepository.save(ChangesSolution.builder()
                .solution(request.getSolusi())
                .status(SolutionStatus.valueOf(request.getStatus()))
                .build());

        changesSolution.setServerChanges(serverChanges);
        serverChanges.setSolution(changesSolution);
        serverChangesRepository.save(serverChanges);
        return new ChangesSolutionResponse(changesSolution.getId(), changesSolution.getSolution(), changesSolution.getStatus().toString());
    }

    @Override
    public ChangesSolutionResponse updateSolution(String id, ChangesSolutionRequest request) {
        ChangesSolution changesSolution = solutionRepository.getReferenceById(id);
        if (!request.getSolusi().isEmpty()) changesSolution.setSolution(request.getSolusi());
        if (!request.getStatus().isEmpty()) changesSolution.setStatus(SolutionStatus.valueOf(request.getStatus()));
        solutionRepository.save(changesSolution);

        return new ChangesSolutionResponse(changesSolution.getId(), changesSolution.getSolution(), changesSolution.getStatus().toString());
    }
}
