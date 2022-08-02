package com.deavensoft.paketomat.courier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("courier")
public interface CourierRepository extends JpaRepository<Courier, Long> {
}
