package com.deavensoft.paketomat.dispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DispatcherService {
    private Dispatcher dispatcher;
    @Autowired
    public DispatcherService (@Qualifier("dispatcher") Dispatcher dispatcher){
        this.dispatcher = dispatcher;
    }
    public List<DispatcherModel> findAllDispatchers(){
        return dispatcher.findAll();
    }
    public void saveDispatcher(DispatcherModel newDispatcher){
        dispatcher.save(newDispatcher);
    }
    public Optional<DispatcherModel> findDispatcherById(Long id){
       return  dispatcher.findById(id);
    }
    public void deleteDispatcherById(Long id){
        dispatcher.deleteById(id);
    }
}
