package com.deavensoft.paketomat.courier;


import java.util.List;
import java.util.Optional;

public interface CourierService {

    public List<Courier> findAllCouriers();

    public void saveCourier(Courier newCourier);

    public Optional<Courier> getCourierById(Long id);

    public void deleteCourierById(Long id);
}
