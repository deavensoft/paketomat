package com.deavensoft.paketomat.dispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/dispatchers")
public class DispatcherController {

    private DispatcherServiceImpl dispatcherServiceImpl;
    @Autowired
    public DispatcherController(DispatcherServiceImpl dispatcherServiceImpl){
        this.dispatcherServiceImpl = dispatcherServiceImpl;
    }
    @GetMapping
    public List<DispatcherModel> findAllDispatchers(){
        return dispatcherServiceImpl.findAllDispatchers();
    }
    @PostMapping
    public int saveDispatcher(@RequestBody DispatcherModel dispatcher){
        dispatcherServiceImpl.saveDispatcher(dispatcher);
        return 1;
    }
    @GetMapping(path = "{/id}")
    public Optional<DispatcherModel> findDispatcherById(@PathVariable(name = "id") Long id){
        return dispatcherServiceImpl.findDispatcherById(id);
    }

    @DeleteMapping(path = "{/id}")
    public int deleteDispatcherById(@PathVariable(name = "id") Long id){
        dispatcherServiceImpl.deleteDispatcherById(id);
        return 1;
    }
}
