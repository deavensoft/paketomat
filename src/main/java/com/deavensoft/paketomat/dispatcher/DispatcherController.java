package com.deavensoft.paketomat.dispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/dispatchers")
public class DispatcherController {

    private DispatcherService dispatcherService;
    @Autowired
    public DispatcherController(DispatcherService dispatcherService){
        this.dispatcherService = dispatcherService;
    }
    @GetMapping
    public List<DispatcherModel> findAllDispatchers(){
        return dispatcherService.findAllDispatchers();
    }
    @PostMapping
    public int saveDispatcher(@RequestBody DispatcherModel dispatcher){
        dispatcherService.saveDispatcher(dispatcher);
        return 1;
    }
    @GetMapping(path = "{id}")
    public Optional<DispatcherModel> findDispatcherById(@PathVariable(name = "id") Long id){
        return dispatcherService.findDispatcherById(id);
    }

    @DeleteMapping(path = "{id}")
    public int deleteDispatcherById(@PathVariable(name = "id") Long id){
        dispatcherService.deleteDispatcherById(id);
        return 1;
    }
}
