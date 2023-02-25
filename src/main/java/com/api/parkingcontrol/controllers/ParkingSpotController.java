package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping("/salvar-registro")
    public ResponseEntity<Object> salvarParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto){

        var parKingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto,parKingSpotModel);
        parKingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));

        try {
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parKingSpotModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }
    @GetMapping("buscar-vagas")
    public ResponseEntity<List<ParkingSpotModel>> buscarTodasAsVagas(){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
    }
}
