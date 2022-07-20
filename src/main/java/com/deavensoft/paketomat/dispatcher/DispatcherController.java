package com.deavensoft.paketomat.dispatcher;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Get dispatchers", description = "Get all dispatchers")
    @ApiResponse(responseCode = "200", description = "All dispatchers are returned")
    @ApiResponse(responseCode = "404", description = "Dispatchers not found")
    @ApiResponse(responseCode = "500", description = "Server fault")
    public List<DispatcherModel> findAllDispatchers(){
        return dispatcherServiceImpl.findAllDispatchers();
    }
    @PostMapping
    @Operation(summary = "Add new dispatcher")
    public int saveDispatcher(@RequestBody DispatcherModel dispatcher){
        dispatcherServiceImpl.saveDispatcher(dispatcher);
        return 1;
    }
    @GetMapping(path = "/{id}")
    @Operation(summary = "Get dispatcher", description = "Get dispatcher with specified id")
    public Optional<DispatcherModel> findDispatcherById(@PathVariable(name = "id") Long id){
        return dispatcherServiceImpl.findDispatcherById(id);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete dispatcher", description = "Delete dispatcher with specified id")
    public int deleteDispatcherById(@PathVariable(name = "id") Long id){
        dispatcherServiceImpl.deleteDispatcherById(id);
        return 1;
    }
}
