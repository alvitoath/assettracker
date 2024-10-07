package propensi.project.Assettrackr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import propensi.project.Assettrackr.model.Server;
import propensi.project.Assettrackr.model.dto.*;
import propensi.project.Assettrackr.service.server.ServerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/server")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ServerController {

    @Autowired
    private ServerService service;

    @PostMapping("/create")
    public ResponseEntity<Server> createServer(CreateUpdateServerRequest request){
        try {
            Server response = service.createServer(request);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

}

