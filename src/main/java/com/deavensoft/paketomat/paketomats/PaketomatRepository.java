package com.deavensoft.paketomat.paketomats;

import com.deavensoft.paketomat.center.model.Paketomat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("paketomat")
public interface PaketomatRepository extends JpaRepository<Paketomat,Long> {

}
