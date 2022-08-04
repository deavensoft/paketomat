package com.deavensoft.paketomat.dispatcher;

import com.deavensoft.paketomat.center.CenterService;
import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.exceptions.NoSuchDispatcherException;
import com.deavensoft.paketomat.exceptions.NoSuchPackageException;
import com.deavensoft.paketomat.exceptions.PaketomatException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/dispatchers")
@Slf4j
public class DispatcherController {

    private final DispatcherService dispatcherService;

    private final CenterService centerService;

    @GetMapping
    @Operation(summary = "Get dispatchers", description = "Get all dispatchers")
    @ApiResponse(responseCode = "200", description = "All dispatchers are returned")
    public List<Dispatcher> findAllDispatchers(){
        log.info("All dispatchersare returned");
        return dispatcherService.findAllDispatchers();
    }
    @PostMapping
    @Operation(summary = "Add new dispatcher")
    @ApiResponse(responseCode = "200", description = "New dispatcher added")
    public int saveDispatcher(@RequestBody Dispatcher dispatcher){
        log.info("New dispatcher is added");
        dispatcherService.saveDispatcher(dispatcher);
        return 1;
    }
    @GetMapping(path = "/{id}")
    @Operation(summary = "Get dispatcher", description = "Get dispatcher with specified id")
    @ApiResponse(responseCode = "200", description = "Dispatcher wwith specified id returned")
    public Optional<Dispatcher> findDispatcherById(@PathVariable(name = "id") Long id) throws NoSuchDispatcherException {
        Optional<Dispatcher> d = dispatcherService.findDispatcherById(id);
        if(d.isEmpty()){
            throw new NoSuchDispatcherException("There is no dispatcher with id " + id, HttpStatus.OK, 200);
        } else{
            String mess = "Dispatcher with id " + id + " is returned";
            log.info(mess);
        }
        return d;
    }

    @PostMapping(path = "/dispatch/{id}")
    @Operation(summary = "Move package for dispatching")
    @ApiResponse(responseCode = "200", description = "Package is ready to be dispatched")
    public void dispatchPackage(@PathVariable(name = "id") Long id) throws PaketomatException, IOException {
        Optional<Package> newPackage = centerService.findPackageById(id);
        if(newPackage.isEmpty())
            throw new NoSuchPackageException("There is no package with id " + id, HttpStatus.OK, 200);
        dispatcherService.delieverPackage(newPackage.get());
        log.info("Package is ready to be dispatched");
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete dispatcher", description = "Delete dispatcher with specified id")
    @ApiResponse(responseCode = "200", description = "Dispatcher with specified id deleted")
    public int deleteDispatcherById(@PathVariable(name = "id") Long id) throws NoSuchDispatcherException {
        try {
            dispatcherService.deleteDispatcherById(id);
            String mess = "Dispatcher with id " + id + " is deleted";
            log.info(mess);
        } catch (EmptyResultDataAccessException e){
            throw new NoSuchDispatcherException("Dispatcher with id " + id + " can't be deleted", HttpStatus.INTERNAL_SERVER_ERROR, 500);
        }
        return 1;
    }
}
