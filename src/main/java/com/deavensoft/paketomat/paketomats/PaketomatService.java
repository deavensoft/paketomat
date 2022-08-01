package com.deavensoft.paketomat.paketomats;


import com.deavensoft.paketomat.center.model.Paketomat;

import java.util.List;

public interface PaketomatService {

    List<Paketomat> getAllPaketomats();

    void savePaketomat(Paketomat paketomat);
    void deleteAll();

}
