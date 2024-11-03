package propensi.project.Assettrackr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import propensi.project.Assettrackr.model.Server;
import propensi.project.Assettrackr.model.dto.request.CreateUpdateServerRequest;
import propensi.project.Assettrackr.model.dto.request.ServerUpdateRequest;
import propensi.project.Assettrackr.model.dto.response.GetServerResponse;
import propensi.project.Assettrackr.model.dto.response.ServerDetailResponse;
import propensi.project.Assettrackr.service.server.ServerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/server")
public class ServerController {

    @Autowired
    private ServerService service;

    @PostMapping("/create")
    public ResponseEntity<?> createServer(@RequestBody CreateUpdateServerRequest request){
        try {
            Server response = service.createServer(request);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/divisi/{divisi}")
    public ResponseEntity<List<GetServerResponse>> getServerByDivisi(@PathVariable("divisi")String divisi){
        try {
            List<GetServerResponse> response = service.getServerDivisi(divisi);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteServer(@PathVariable("id") Integer id){
        try {
            String response = service.deleteServer(id);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateServer(@PathVariable("id") Integer id, @RequestBody ServerUpdateRequest request){
        try {
            boolean result = service.updateServer(id, request);
            if (result) return ResponseEntity.ok("success");
            else return ResponseEntity.badRequest().body("Data is not valid");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServerDetailResponse> detailSever(@PathVariable("id") Integer id){
        try {
            ServerDetailResponse response= service.getServerDetail(id);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<GetServerResponse>> getAllServer(){
        try {
            List<GetServerResponse> response = service.getAllServer();
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }
}

