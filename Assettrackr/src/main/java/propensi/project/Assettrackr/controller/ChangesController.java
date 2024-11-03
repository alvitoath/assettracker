package propensi.project.Assettrackr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import propensi.project.Assettrackr.model.dto.RestResponse;
import propensi.project.Assettrackr.model.dto.request.CreateChangesRequest;
import propensi.project.Assettrackr.model.dto.response.ServerChangesResponse;
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

    @GetMapping("/detail/{changesId}")
    public ResponseEntity<RestResponse> getServerChangesDetail(@PathVariable("changesId") String id){
        try {
            ServerChangesResponse data = service.getServerChangesDetail(id);
            RestResponse response = new RestResponse("Here is your data", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            RestResponse response = new RestResponse(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
