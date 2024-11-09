package propensi.project.Assettrackr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import propensi.project.Assettrackr.model.dto.RestResponse;
import propensi.project.Assettrackr.model.dto.response.ChangesSolutionResponse;
import propensi.project.Assettrackr.service.solution.SolutionService;
import propensi.project.Assettrackr.model.dto.request.ChangesSolutionRequest;

@RestController
@RequestMapping("/api/v1/solution")
public class SolutionController {
    @Autowired
    private SolutionService service;

    @PostMapping("/create/{changeId}")
    private ResponseEntity<RestResponse> createSolution(@PathVariable("changeId") String id, @RequestBody ChangesSolutionRequest request){
        try {
            ChangesSolutionResponse data = service.createSolution(id, request);
            RestResponse response = new RestResponse("Solusi berhasil dibuat", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            RestResponse response = new RestResponse(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{solutionId}")
    private ResponseEntity<RestResponse> updateSolution(@PathVariable("solutionId") String id, @RequestBody ChangesSolutionRequest request){
        try {
            ChangesSolutionResponse data = service.updateSolution(id, request);
            RestResponse response = new RestResponse("Solusi berhasil diubah", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            RestResponse response = new RestResponse(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
