package propensi.project.Assettrackr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import propensi.project.Assettrackr.model.Divisi;
import propensi.project.Assettrackr.model.dto.RestResponse;
import propensi.project.Assettrackr.model.dto.request.CreateDeveloperRequest;
import propensi.project.Assettrackr.model.dto.request.UpdateDeveloperRequest;
import propensi.project.Assettrackr.model.dto.response.DeveloperResponse;
import propensi.project.Assettrackr.service.developer.DeveloperService;

@RestController
@RequestMapping("/api/v1/developer")
public class DeveloperController {
    @Autowired
    private DeveloperService service;

    @PostMapping("/create")
    public ResponseEntity<RestResponse> createDeveloper(@RequestBody CreateDeveloperRequest request){
        DeveloperResponse data = service.createDeveloper(request);
        RestResponse response = new RestResponse("Developer successfully created", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

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
}
