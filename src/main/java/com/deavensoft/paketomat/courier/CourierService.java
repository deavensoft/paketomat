package com.deavensoft.paketomat.courier;

import java.util.List;
import java.util.Optional;

public interface CourierService {

    public List<CourierModel> findAllCouriers();

    public void saveCourier(CourierModel newCourier);

    public Optional<CourierModel> getCourierById(Long id);

    public void deleteCourierById(Long id);
}
