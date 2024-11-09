package propensi.project.Assettrackr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import propensi.project.Assettrackr.model.Divisi;
import propensi.project.Assettrackr.model.Server;
import propensi.project.Assettrackr.model.dto.request.CreateUpdateDivisiRequest;
import propensi.project.Assettrackr.service.divisi.DivisiService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/divisi")
public class DivisiController {

    @Autowired
    private DivisiService service;
    @PostMapping("/create")
    public ResponseEntity<?> createDivisi(@RequestBody CreateUpdateDivisiRequest request){
        try {
            Divisi response = service.createDivisi(request);
            if (response == null) return ResponseEntity.badRequest().body(null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Divisi>> getAllDivisi(){
        List<Divisi> response = service.getAllDivisi();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDivisi(@PathVariable("id") String id){
        try {
            String response = service.deleteDivisi(id);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDivisi(@PathVariable("id")String id, @RequestBody CreateUpdateDivisiRequest request){
        try {
            Divisi response = service.updateDivisi(id, request);
            if (response == null) return ResponseEntity.badRequest().body(null);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/divisi/{nama}")
    public ResponseEntity<List<Server>> getServerByDivisiName(@PathVariable("nama")String nama){
        try {
            List<Server> response = service.getServerByDivisi(nama);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }

    }

    @GetMapping("/divisi/{id}")
    public ResponseEntity<List<Server>> getServerByDivisiId(@PathVariable("id")String id){
        try {
            List<Server> response = service.getServerByDivisiId(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }

    }
}
