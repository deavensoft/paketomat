package com.deavensoft.paketomat.dispatcher;
import java.util.List;
import java.util.Optional;

public interface DispatcherService {

     List<DispatcherModel> findAllDispatchers();

     void saveDispatcher(DispatcherModel newDispatcher);

     Optional<DispatcherModel> findDispatcherById(Long id);

     void deleteDispatcherById(Long id);
}
