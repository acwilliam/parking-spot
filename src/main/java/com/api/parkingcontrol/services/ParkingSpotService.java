package com.api.parkingcontrol.services;

import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ParkingSpotService {


    final ParkingSpotRepository parkingSpotRepository;


    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parKingSpotModel) throws Exception {

        if(existsByLicensePlateCar(parKingSpotModel.getLicensePlateCar())) {
                throw new Exception("Conflito: License car ja em uso");
        }else if (existsByParkingSpotNumber(parKingSpotModel.getParkingSpotNumber())) {
            throw new Exception("Conflito: Vaga ja esta em uso");
        }else if (existsByApartmetAndBlock(parKingSpotModel.getApartment(), parKingSpotModel.getBlock())) {
            throw new Exception("Conflito: Vaga ja esta registrada para o apartamento");
        }

        return parkingSpotRepository.save(parKingSpotModel);

    }

    private boolean existsByLicensePlateCar(String licensePlateCar) throws Exception {
            return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }

    private boolean existsByParkingSpotNumber(String parkingSpotNumber) throws Exception {
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    private boolean existsByApartmetAndBlock(String apartment, String block) throws Exception {
        return parkingSpotRepository.existsByApartmentAndBlock(apartment,block);
    }
}
