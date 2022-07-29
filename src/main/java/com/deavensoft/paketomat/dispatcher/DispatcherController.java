package com.deavensoft.paketomat.dispatcher;

import com.deavensoft.paketomat.exceptions.NoSuchDispatcherException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public Optional<DispatcherModel> findDispatcherById(@PathVariable(name = "id") Long id) throws NoSuchDispatcherException {
        Optional<DispatcherModel> d = dispatcherServiceImpl.findDispatcherById(id);
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
            dispatcherServiceImpl.deleteDispatcherById(id);
            String mess = "Dispatcher with id " + id + " is deleted";
            log.info(mess);
        } catch (EmptyResultDataAccessException e){
            throw new NoSuchDispatcherException("Dispatcher with id " + id + " can't be deleted", HttpStatus.INTERNAL_SERVER_ERROR, 500);
        }
        return 1;
    }
    @GetMapping("/getDistance")
    public int getDistance() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://distanceto.p.rapidapi.com/get?route=%3CREQUIRED%3E&car=false")
                .get()
                .addHeader("X-RapidAPI-Key", "a29708a58bmsh6a9c06d468885e0p1c7b96jsn87d23450a8a3")
                .addHeader("X-RapidAPI-Host", "distanceto.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().contentLength());
        return 1;
    }
}
