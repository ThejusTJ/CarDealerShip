package com.nissan.car.dealership.service;

import com.nissan.car.dealership.request.VehicleRequest;
import com.nissan.car.dealership.response.CommonResponse;
import com.nissan.car.dealership.response.VehicleResponse;

import java.util.List;

public interface VehicleService {

     CommonResponse saveVehicle(VehicleRequest vehicleRequest);

     CommonResponse editVehicle(VehicleRequest vehicleRequest, Long id);

     CommonResponse deleteVehicle(Long id);

     List<VehicleResponse> getAllVehicles();

}
