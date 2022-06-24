package com.nissan.car.dealership.controller;

import com.nissan.car.dealership.request.VehicleRequest;
import com.nissan.car.dealership.response.CommonResponse;
import com.nissan.car.dealership.response.VehicleResponse;
import com.nissan.car.dealership.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping(value = "/save")
    public ResponseEntity<CommonResponse> saveVehicle(@RequestBody VehicleRequest vehicleRequest){
        return  new ResponseEntity<>(vehicleService.saveVehicle(vehicleRequest), HttpStatus.OK);
    }

    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<CommonResponse> editVehicle(@RequestBody VehicleRequest vehicleRequest, @PathVariable Long id){
        return  new ResponseEntity<>(vehicleService.editVehicle(vehicleRequest,id), HttpStatus.OK);
    }

    @GetMapping(value = "/delete/{id}")
    public ResponseEntity<CommonResponse> deleteVehicle(@PathVariable Long id){
        return  new ResponseEntity<>(vehicleService.deleteVehicle(id), HttpStatus.OK);
    }

    @GetMapping(value = "/getAllVehicles")
    public ResponseEntity<List<VehicleResponse>> getAllVehicles(){
        return  new ResponseEntity<>(vehicleService.getAllVehicles(), HttpStatus.OK);
    }

}
