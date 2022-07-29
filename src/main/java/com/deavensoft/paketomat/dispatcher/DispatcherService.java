package com.deavensoft.paketomat.dispatcher;

import org.springframework.data.geo.Distance;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DispatcherService {

    public List<DispatcherModel> findAllDispatchers();

    public void saveDispatcher(DispatcherModel newDispatcher);

    public Optional<DispatcherModel> findDispatcherById(Long id);

    public void deleteDispatcherById(Long id);




}
