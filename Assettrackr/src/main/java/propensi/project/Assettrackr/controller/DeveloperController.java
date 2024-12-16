package propensi.project.Assettrackr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import propensi.project.Assettrackr.model.dto.RestResponse;
import propensi.project.Assettrackr.model.dto.request.CreateDeveloperRequest;
import propensi.project.Assettrackr.model.dto.request.UpdateDeveloperRequest;
import propensi.project.Assettrackr.model.dto.response.DeveloperResponse;
import propensi.project.Assettrackr.service.developer.DeveloperService;
import propensi.project.Assettrackr.exception.DuplicateDeveloperNameException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/developer")
public class DeveloperController {
    @Autowired
    private DeveloperService service;

    @PostMapping("/create")
    public ResponseEntity<RestResponse> createDeveloper(@RequestBody CreateDeveloperRequest request){
        try {
            DeveloperResponse data = service.createDeveloper(request);
            RestResponse response = new RestResponse("Developer successfully created", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DuplicateDeveloperNameException e) {
            RestResponse response = new RestResponse(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            RestResponse response = new RestResponse("Terjadi kesalahan", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @PostMapping("/create")
//    public ResponseEntity<RestResponse> createDeveloper(@RequestBody CreateDeveloperRequest request){
//        DeveloperResponse data = service.createDeveloper(request);
//        RestResponse response = new RestResponse("Developer successfully created", data);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @PutMapping("/update/{developerId}")
    public ResponseEntity<RestResponse> updateDeveloper(@RequestBody UpdateDeveloperRequest request, @PathVariable("developerId")String id){
        try {
            DeveloperResponse data = service.updateDeveloper(request, id);
            RestResponse response = new RestResponse("Successfuly update data", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            RestResponse response = new RestResponse("Data is invalid", null);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/detail/{developerId}")
    public ResponseEntity<RestResponse> getDetailDeveloper(@PathVariable("developerId")String id){
        try {
            DeveloperResponse data = service.getDetailDeveloper(id);
            RestResponse response = new RestResponse("Successfuly update data", data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            RestResponse response = new RestResponse("Id not found", null);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<RestResponse> getAllDeveloper(){
        List<DeveloperResponse> data = service .getAllDeveloper();
        RestResponse response = new RestResponse("Here is your data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{developerId}")
    public ResponseEntity<RestResponse> deleteDeveloper(@PathVariable("developerId") String id){
        try {
            Boolean data = service.deleteDeveloper(id);

            if (data) {
                RestResponse response = new RestResponse("Successfuly delete data", null);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            RestResponse response = new RestResponse("Somewthing went wrong", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            RestResponse response = new RestResponse("Id not found", null);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
