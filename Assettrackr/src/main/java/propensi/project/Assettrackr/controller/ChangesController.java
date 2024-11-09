package propensi.project.Assettrackr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import propensi.project.Assettrackr.model.dto.RestResponse;
import propensi.project.Assettrackr.model.dto.request.ChangesSolutionRequest;
import propensi.project.Assettrackr.model.dto.request.CreateChangesRequest;
import propensi.project.Assettrackr.model.dto.request.UpdateChangesRequest;
import propensi.project.Assettrackr.model.dto.response.DeveloperResponse;
import propensi.project.Assettrackr.model.dto.response.ServerChangesResponse;
import propensi.project.Assettrackr.service.changes.ServerChangesService;

import java.util.List;

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

    @PutMapping("/update/{changesId}")
    public ResponseEntity<RestResponse> updateServerChanges(@PathVariable("changesId")String id, @RequestBody UpdateChangesRequest request){
        try {
            ServerChangesResponse data = service.updateServerChanges(id, request);
            RestResponse response = new RestResponse("Permintaan berhasil diubah dan disimpan!", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            RestResponse response = new RestResponse(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/delete/{changeId}")
    public ResponseEntity<RestResponse> deleteServerChanges(@PathVariable("changeId") String id){
        try {
            Boolean data = service.deleteServer(id);
            if (data){
                RestResponse response = new RestResponse("Perubahan berhasil dihapus", null);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            RestResponse response = new RestResponse("Perubahan tidak dapat dihapus", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            RestResponse response = new RestResponse(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/divisi/{divisiId}")
    public ResponseEntity<RestResponse> getServerChangesByDivisi(@PathVariable("divisiId") String id){
        List<ServerChangesResponse> data = service.getServerChangesByDivisi(id);
        RestResponse response = new RestResponse("Here is your data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/divisi/name/{divisiName}")
    public ResponseEntity<RestResponse> getServerChangesByDivisiName(@PathVariable("divisiName") String name){
        List<ServerChangesResponse> data = service.getServerChangesByDivisiName(name);
        RestResponse response = new RestResponse("Here is your data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<RestResponse> getAllDeveloper(){
        List<ServerChangesResponse> data = service.getAll();
        RestResponse response = new RestResponse("Here is your data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
