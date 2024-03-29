package com.api.parkingcontrol.controller;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping("/salvar-registro")
    public ResponseEntity<Object> salvarVagas(@RequestBody @Valid ParkingSpotDto parkingSpotDto){

        var parKingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto,parKingSpotModel);
        parKingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));

        try {
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parKingSpotModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }
    @GetMapping("/buscar-vagas")
    public ResponseEntity<Page<ParkingSpotModel>> buscarTodasAsVagas(@PageableDefault(page = 0, size = 10, sort = "registrationDate", direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable));
    }
    @GetMapping("/id")
    public ResponseEntity<Object> buscarPorUmaVaga(@RequestHeader(value = "id") @Valid UUID id) throws Exception {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (parkingSpotModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga não encontrada");
        }

        return  ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());

    }

    @DeleteMapping("/id")
    public ResponseEntity<Object> deletarUmRegistro(@RequestHeader(value = "id") @Valid UUID id) throws Exception {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (parkingSpotModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga não encontrada");
        }
        parkingSpotService.delete(parkingSpotModelOptional.get());
        return  ResponseEntity.status(HttpStatus.OK).body("Vaga deletada com sucesso");
    }

    @PutMapping("/id")
    public ResponseEntity<Object> atualizarRegistro(@RequestHeader(value = "id") @Valid UUID id,
                                                    @RequestBody @Valid ParkingSpotDto parkingSpotDto) throws Exception {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (parkingSpotModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga não encontrada");
        }

        var  parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto,parkingSpotModel);
        parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
        parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());

        return  ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
    }
}
