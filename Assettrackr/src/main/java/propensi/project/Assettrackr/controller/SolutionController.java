package propensi.project.Assettrackr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import propensi.project.Assettrackr.model.dto.RestResponse;
import propensi.project.Assettrackr.service.solution.SolutionService;
import propensi.project.Assettrackr.model.dto.request.ChangesSolutionRequest;

@RestController
@RequestMapping("/api/v1/solution")
public class SolutionController {
    @Autowired
    private SolutionService service;

    @PostMapping("/create/{changeId}")
    private ResponseEntity<RestResponse> createSolution(@PathVariable("changeId") String id, @RequestBody ChangesSolutionRequest request){
        return null;
    }
}
