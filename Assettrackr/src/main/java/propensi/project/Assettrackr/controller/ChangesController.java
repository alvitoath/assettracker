package propensi.project.Assettrackr.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import propensi.project.Assettrackr.model.dto.RestResponse;
import propensi.project.Assettrackr.model.dto.request.*;
import propensi.project.Assettrackr.model.dto.response.FinishedChangesResponse;
import propensi.project.Assettrackr.model.dto.response.ChartResponse;
import propensi.project.Assettrackr.model.dto.response.ServerChangesResponse;
import propensi.project.Assettrackr.service.changes.ServerChangesService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/changes")
public class ChangesController {

    @Autowired
    private ServerChangesService service;

    @PostMapping("/create")
    public ResponseEntity<ServerChangesResponse> createServerChanges(@RequestBody CreateChangesRequest request, HttpServletRequest servletRequest){
        try {
            String headerAuth = servletRequest.getHeader("Authorization");
            String token;
            if (headerAuth.startsWith("Bearer")){
                token = headerAuth.substring(7);
            } else {
                token = headerAuth;
            }
            ServerChangesResponse response =  service.createServerChanges(request, token);
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

    @GetMapping("/finish/{divisiId}")
    public ResponseEntity<RestResponse> getFinishedChangesByDivisiId(@PathVariable("divisiId") String id){
        List<FinishedChangesResponse> data = service.getAllFinishByDivisiId(id);
        RestResponse response = new RestResponse("Here is your data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/operational")
    public ResponseEntity<RestResponse> getServerChangeOperational(){
        List<FinishedChangesResponse> data = service.getAllSolvedSolution();
        RestResponse response = new RestResponse("Here is your data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/operational/{changesId}")
    public ResponseEntity<RestResponse> getServerChangeOperationalById(@PathVariable("changesId") String changesId){
        FinishedChangesResponse data = service.getFinishById(changesId);
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
    public ResponseEntity<RestResponse> getAllServerChanges(){
        List<FinishedChangesResponse> data = service.getAll();
        RestResponse response = new RestResponse("Here is your data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get/{changesId}")
    public ResponseEntity<RestResponse> getServerChangesById(@PathVariable("changesId") String changesId){
        try {
            FinishedChangesResponse data = service.getFinishById(changesId);
            RestResponse response = new RestResponse("Here is your data", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            RestResponse response = new RestResponse(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/reject/{changesId}")
    public ResponseEntity<RestResponse> rejectServerChanges(@PathVariable("changesId") String changesId){
        try {
            Boolean data = service.rejectServerChanges(changesId);
            if (data){
                RestResponse response = new RestResponse("Perubahan berhasil ditolak", null);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            RestResponse response = new RestResponse("Perubahan tidak dapat ditolak", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            RestResponse response = new RestResponse(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/assign/{serverChangesId}")
    public ResponseEntity<RestResponse> assignDeveloper(@PathVariable("serverChangesId")String changesId, @RequestBody AssignUpdateDeveloperRequest request){
        try {
            FinishedChangesResponse data = service.assingDeveloper(changesId, request);
            RestResponse response = new RestResponse("Here is your data!", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e){
            RestResponse response = new RestResponse(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e){
            RestResponse response = new RestResponse(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/developer/update/{serverChangesId}")
    public ResponseEntity<RestResponse> updateDeveloper(@PathVariable("serverChangesId")String serverChangeId, @RequestBody AssignUpdateDeveloperRequest request){
        try {
            FinishedChangesResponse data = service.updateDeveloper(serverChangeId, request);
            RestResponse response = new RestResponse("Permintaan berhasil diubah dan disimpan!", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            RestResponse response = new RestResponse(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/finish/{changesId}")
    public ResponseEntity<RestResponse> finishChanges(@PathVariable("changesId")String changesId){
        try {
            FinishedChangesResponse data = service.finishChanges(changesId);
            RestResponse response = new RestResponse("Permintaan berhasil diubah dan disimpan!", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            RestResponse response = new RestResponse(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/summary/day")
    public ResponseEntity<RestResponse> getDailySummary(@RequestBody SummaryRequest request){
        ChartResponse data = service.getLineGraphDailySummary(request);
        RestResponse response = new RestResponse("Here is your data!", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/summary/month")
    public ResponseEntity<RestResponse> getMonthlySummary(@RequestBody SummaryRequest request){
        ChartResponse data = service.getLineGraphMonthlySummary(request);
        RestResponse response = new RestResponse("Here is your data!", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
