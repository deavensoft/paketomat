package com.deavensoft.paketomat.courier;

import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.exceptions.PaketomatException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface CourierService {

     List<Courier> findAllCouriers();

     void saveCourier(Courier newCourier);

     Optional<Courier> getCourierById(Long id);

     void deleteCourierById(Long id);

     List<Package> getPackagesForCourier(String city) throws PaketomatException, UnsupportedEncodingException;

     List<Package> getNotPickedUpPackages();

     List<Package> returnNotPickedUpPackages();
}
