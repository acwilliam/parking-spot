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
    public ParkingSpotModel save(ParkingSpotModel parKingSpotModel) {

        return parkingSpotRepository.save(parKingSpotModel);

    }

    public boolean existsByLicensePlateCar(String licensePlateCar) throws Exception {

       try {

       return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
       } catch (Exception e) {
           throw new Exception("Conflito: License car ja em uso");
       }

    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber) throws Exception {

        try{

        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
        }catch (Exception e) {
            throw new Exception("Conflito: Vaga ja esta em uso");
        }


    }

    public boolean existsByApartmetAndBlock(String apartment, String block) throws Exception {

        try {

        return parkingSpotRepository.existsByApartmentAndBlock(apartment,block);
        } catch (Exception e) {
            throw new Exception("Conflito: Vaga ja esta registrada para o apartamento");
        }

    }
}
