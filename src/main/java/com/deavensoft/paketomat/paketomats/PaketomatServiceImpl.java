package com.deavensoft.paketomat.paketomats;

import com.deavensoft.paketomat.center.model.Paketomat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
public class PaketomatServiceImpl implements PaketomatService{

    private final PaketomatRepository paketomatRepository;

//    @Autowired
//    public PaketomatServiceImpl(@Qualifier("paketomat") PaketomatRepository paketomatRepository){
//        this.paketomatRepository = paketomatRepository;
//    }

    @Override
    public List<Paketomat> getAllPaketomats() {
        return paketomatRepository.findAll();
    }

    @Override
    public void savePaketomat(Paketomat paketomat) { paketomatRepository.saveAndFlush(paketomat); }

    @Override
    public void deleteAll() {
        paketomatRepository.deleteAll();
    }

}
