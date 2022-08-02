package com.deavensoft.paketomat.courier;


import com.deavensoft.paketomat.center.model.Package;

import java.util.List;
import java.util.Optional;

public interface CourierService {

     List<CourierModel> findAllCouriers();

     void saveCourier(CourierModel newCourier);

     Optional<CourierModel> getCourierById(Long id);

     void deleteCourierById(Long id);

}
