package propensi.project.Assettrackr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import propensi.project.Assettrackr.model.dto.CreateChangesRequest;
import propensi.project.Assettrackr.model.dto.ServerChangesResponse;
import propensi.project.Assettrackr.service.changes.ServerChangesService;

@RestController
@RequestMapping("/api/v1/changes")
public class ChangesController {

    @Autowired
    private ServerChangesService service;

    @PostMapping("/create")
    public ResponseEntity<ServerChangesResponse> createServerChanges(@RequestBody CreateChangesRequest request){
        try {
            ServerChangesResponse response =  service.createServerChanges(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
