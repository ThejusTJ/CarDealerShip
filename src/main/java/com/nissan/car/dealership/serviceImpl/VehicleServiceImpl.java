package com.nissan.car.dealership.serviceImpl;

import com.nissan.car.dealership.entity.Vehicle;
import com.nissan.car.dealership.exception.ResourceNotCreatedException;
import com.nissan.car.dealership.exception.ResourceNotFoundException;
import com.nissan.car.dealership.repository.VehicleRepository;
import com.nissan.car.dealership.request.VehicleRequest;
import com.nissan.car.dealership.response.CommonResponse;
import com.nissan.car.dealership.response.VehicleResponse;
import com.nissan.car.dealership.service.VehicleService;
import com.nissan.car.dealership.utils.DealershipConstants;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public CommonResponse saveVehicle(VehicleRequest vehicleRequest) {
        Vehicle vehicle = new Vehicle();
        CommonResponse response = new CommonResponse();
        try {
            mapVehicleRequest(vehicle, vehicleRequest);
            vehicleRepository.save(vehicle);
            response.setMessage(DealershipConstants.VEHICLE_SAVE);

        } catch (NullPointerException | ConstraintViolationException | DataIntegrityViolationException exception) {
            throw new ResourceNotCreatedException(exception.getMessage());
        }
        return response;
    }

    private void mapVehicleRequest(Vehicle vehicle, VehicleRequest vehicleRequest) {

        if (Objects.nonNull(vehicleRequest.getMake())) {
            vehicle.setMake(vehicleRequest.getMake());
        }

        if (Objects.nonNull(vehicleRequest.getModel())) {
            vehicle.setModel(vehicleRequest.getModel());
        }

        if (Objects.nonNull(vehicleRequest.getColour())) {
            vehicle.setColour(vehicleRequest.getColour());
        }

        if (Objects.nonNull(vehicleRequest.getPrice())) {
            vehicle.setPrice(vehicleRequest.getPrice());
        }
    }

    @Override
    public CommonResponse editVehicle(VehicleRequest vehicleRequest, Long id) {
        CommonResponse response = new CommonResponse();
        try {
            Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
            if (optionalVehicle.isPresent()) {
                Vehicle vehicle = optionalVehicle.get();
                mapVehicleRequest(vehicle, vehicleRequest);
                vehicleRepository.save(vehicle);
                response.setMessage(DealershipConstants.VEHICLE_UPDATE);
            } else {
                throw new ResourceNotFoundException(DealershipConstants.VEHICLE_NOT_FOUND);
            }
        } catch (ConstraintViolationException | DataIntegrityViolationException exception) {
            throw new ResourceNotCreatedException(exception.getMessage());
        }
        return response;
    }

    @Override
    public CommonResponse deleteVehicle(Long id) {
        CommonResponse response = new CommonResponse();
        try {
            vehicleRepository.deleteById(id);
            response.setMessage(DealershipConstants.VEHICLE_DELETE);
        } catch (NullPointerException | DataIntegrityViolationException | EmptyResultDataAccessException exception) {
            throw new ResourceNotCreatedException(exception.getMessage());
        }
        return response;
    }

    @Override
    public List<VehicleResponse> getAllVehicles() {
        List<VehicleResponse> vehicleList = new ArrayList<>();
        vehicleRepository.findAll().forEach(vehicle -> {
                    VehicleResponse vehicleResponse = new VehicleResponse();
                    mapVehicleResponse(vehicleResponse, vehicle);
                    vehicleList.add(vehicleResponse);
                }
        );

        if (vehicleList.isEmpty()) {
            throw new ResourceNotFoundException(DealershipConstants.VEHICLE_NOT_FOUND);
        } else {
            return vehicleList;
        }
    }

    private void mapVehicleResponse(VehicleResponse vehicleResponse, Vehicle vehicle) {

        vehicleResponse.setVehicleId(vehicle.getVehicleId());
        vehicleResponse.setColour(vehicle.getColour());
        vehicleResponse.setMake(vehicle.getMake());
        vehicleResponse.setModel(vehicle.getModel());
        vehicleResponse.setPrice(vehicle.getPrice());

    }
}
