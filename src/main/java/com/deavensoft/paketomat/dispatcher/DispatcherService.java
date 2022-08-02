package com.deavensoft.paketomat.dispatcher;

import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.exceptions.NoSuchUserException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DispatcherService{

     List<Dispatcher> findAllDispatchers();

     void saveDispatcher(Dispatcher newDispatcher);

     Optional<Dispatcher> findDispatcherById(Long id);

     void deleteDispatcherById(Long id);


    void delieverPackage(Package newPackage) throws IOException, NoSuchUserException;

}
