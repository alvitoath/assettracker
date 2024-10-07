package propensi.project.Assettrackr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import propensi.project.Assettrackr.model.Divisi;
import propensi.project.Assettrackr.model.Server;
import propensi.project.Assettrackr.model.dto.CreateUpdateDivisiRequest;
import propensi.project.Assettrackr.service.divisi.DivisiService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/divisi")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DivisiController {

    @Autowired
    private DivisiService service;
    @PostMapping("/create")
    public ResponseEntity<Divisi> createDivisi(@RequestBody CreateUpdateDivisiRequest request){
        try {
            Divisi response = service.createDivisi(request);
            if (response == null) return ResponseEntity.badRequest().body(null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Divisi>> getAllDivisi(){
        List<Divisi> response = service.getAllDivisi();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDivisi(@PathVariable("id") Integer id){
        try {
            String response = service.deleteDivisi(id);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Divisi> updateDivisi(@PathVariable("id")Integer id, @RequestBody CreateUpdateDivisiRequest request){
        try {
            Divisi response = service.updateDivisi(id, request);
            if (response == null) return ResponseEntity.badRequest().body(null);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/divisi/{nama}")
    public ResponseEntity<List<Server>> getServerByDivisi(@PathVariable("nama")String nama){
        try {
            List<Server> response = service.getServerByDivisi(nama);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }

    }
}
