package com.deavensoft.paketomat.paketomats;

import com.deavensoft.paketomat.center.model.City;
import com.deavensoft.paketomat.center.model.Paketomat;

import java.util.List;

public interface PaketomatService {

    List<Paketomat> getAllPaketomats();

    void savePaketomats(Paketomat paketomat);

}