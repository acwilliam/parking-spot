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

      //  if(parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())) {
      //      return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: License car ja em uso");
      //  }

      //  if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
      //      return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Vaga ja esta em uso");
      //  }

     //   if(parkingSpotService.existsByApartmetAndBlock(parkingSpotDto.getApartment(),parkingSpotDto.getBlock())) {
     //       return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Vaga ja esta registrada para o apartamento");
     //   }


        var parKingSpotModel = new ParkingSpotModel();

        BeanUtils.copyProperties(parkingSpotDto,parKingSpotModel);

        parKingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));

        try {

        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parKingSpotModel));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
        }


    }
}
