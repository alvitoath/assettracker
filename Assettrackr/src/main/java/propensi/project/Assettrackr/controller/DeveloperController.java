package propensi.project.Assettrackr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import propensi.project.Assettrackr.model.dto.RestResponse;
import propensi.project.Assettrackr.model.dto.request.CreateDeveloperRequest;
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
}
