package com.deavensoft.paketomat.dispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DispatcherServiceImpl implements DispatcherService {
    private DispatcherRepository dispatcherRepository;
    @Autowired
    public DispatcherServiceImpl(@Qualifier("dispatcher") DispatcherRepository dispatcherRepository){
        this.dispatcherRepository = dispatcherRepository;
    }
    public List<DispatcherModel> findAllDispatchers(){
        return dispatcherRepository.findAll();
    }
    public void saveDispatcher(DispatcherModel newDispatcher){
        dispatcherRepository.save(newDispatcher);
    }
    public Optional<DispatcherModel> findDispatcherById(Long id){
       return  dispatcherRepository.findById(id);
    }
    public void deleteDispatcherById(Long id){
        dispatcherRepository.deleteById(id);
    }
}
