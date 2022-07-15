package com.deavensoft.paketomat.center.service;

import com.deavensoft.paketomat.center.dao.iCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CenterService {
    private iCenter center;
    @Autowired
    public CenterService (@Qualifier("center") iCenter iCenter){
        this.center = iCenter;
    }
}
