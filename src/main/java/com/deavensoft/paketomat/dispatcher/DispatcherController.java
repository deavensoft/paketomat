package com.deavensoft.paketomat.dispatcher;

import com.deavensoft.paketomat.exceptions.NoSuchDispatcherException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/dispatchers")
@Slf4j
public class DispatcherController {

    private DispatcherService dispatcherService;

    @GetMapping
    @Operation(summary = "Get dispatchers", description = "Get all dispatchers")
    @ApiResponse(responseCode = "200", description = "All dispatchers are returned")
    public List<DispatcherModel> findAllDispatchers(){
        log.info("All dispatchersare returned");
        return dispatcherService.findAllDispatchers();
    }
    @PostMapping
    @Operation(summary = "Add new dispatcher")
    @ApiResponse(responseCode = "200", description = "New dispatcher added")
    public int saveDispatcher(@RequestBody DispatcherModel dispatcher){
        log.info("New dispatcher is added");
        dispatcherService.saveDispatcher(dispatcher);
        return 1;
    }
    @GetMapping(path = "/{id}")
    @Operation(summary = "Get dispatcher", description = "Get dispatcher with specified id")
    @ApiResponse(responseCode = "200", description = "Dispatcher wwith specified id returned")
    public Optional<DispatcherModel> findDispatcherById(@PathVariable(name = "id") Long id) throws NoSuchDispatcherException {
        Optional<DispatcherModel> d = dispatcherService.findDispatcherById(id);
        if(d.isEmpty()){
            throw new NoSuchDispatcherException("There is no dispatcher with id " + id, HttpStatus.OK, 200);
        } else{
            String mess = "Dispatcher with id " + id + " is returned";
            log.info(mess);
        }
        return d;
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
