package com.deavensoft.paketomat.center.api;

import com.deavensoft.paketomat.center.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CenterController {
    private CenterService centerService;

    @Autowired
    public CenterController (CenterService centerService){
        this.centerService = centerService;
    }

}
