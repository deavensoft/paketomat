package com.deavensoft.paketomat.dispatcher;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("api/dispatchers")
@Slf4j
public class DispatcherController {

    private DispatcherServiceImpl dispatcherServiceImpl;
    @Autowired
    public DispatcherController(DispatcherServiceImpl dispatcherServiceImpl){
        this.dispatcherServiceImpl = dispatcherServiceImpl;
    }
    @GetMapping
    @Operation(summary = "Get dispatchers", description = "Get all dispatchers")
    @ApiResponse(responseCode = "200", description = "All dispatchers are returned")
    public List<DispatcherModel> findAllDispatchers(){
        log.info("All dispatchersare returned");
        return dispatcherServiceImpl.findAllDispatchers();
    }
    @PostMapping
    @Operation(summary = "Add new dispatcher")
    @ApiResponse(responseCode = "200", description = "New dispatcher added")
    public int saveDispatcher(@RequestBody DispatcherModel dispatcher){
        log.info("New dispatcher is added");
        dispatcherServiceImpl.saveDispatcher(dispatcher);
        return 1;
    }
    @GetMapping(path = "/{id}")
    @Operation(summary = "Get dispatcher", description = "Get dispatcher with specified id")
    @ApiResponse(responseCode = "200", description = "Dispatcher wwith specified id returned")
    public Optional<DispatcherModel> findDispatcherById(@PathVariable(name = "id") Long id){
        Optional<DispatcherModel> d = dispatcherServiceImpl.findDispatcherById(id);
        if(d.isEmpty()){
            String mess = "There is no dispatcher with id " + id;
            log.info(mess);
        } else{
            String mess = "Dispatcher with id " + id + " is returned";
            log.info(mess);
        }
        return d;
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete dispatcher", description = "Delete dispatcher with specified id")
    @ApiResponse(responseCode = "200", description = "Dispatcher with specified id deleted")
    public int deleteDispatcherById(@PathVariable(name = "id") Long id){
        try {
            dispatcherServiceImpl.deleteDispatcherById(id);
            String mess = "Dispatcher with id " + id + " is deleted";
            log.info(mess);
        } catch (NoSuchElementException e){
            String mess = "There is no dispatcher with id " + id;
            log.error(mess);
        }
        return 1;
    }
}
