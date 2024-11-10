package propensi.project.Assettrackr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @Autowired
    private final ObjectMapper objectMapper;

    public DivisiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDivisi(@RequestParam("divisiData") String divisiDataJson, @RequestParam("image")MultipartFile image){
        try {
            CreateUpdateDivisiRequest request = objectMapper.readValue(divisiDataJson, CreateUpdateDivisiRequest.class);
            Divisi response = service.createDivisi(request, image);
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
    public ResponseEntity<?> updateDivisi(@PathVariable("id")String id, @RequestParam("divisiData") String divisiDataJson, @RequestParam("image")MultipartFile image){
        try {
            CreateUpdateDivisiRequest request = objectMapper.readValue(divisiDataJson, CreateUpdateDivisiRequest.class);
            Divisi response = service.updateDivisi(id, request, image);
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
