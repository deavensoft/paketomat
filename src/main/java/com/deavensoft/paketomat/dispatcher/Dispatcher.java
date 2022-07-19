package com.deavensoft.paketomat.dispatcher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("dispatcher")
public interface Dispatcher extends JpaRepository<DispatcherModel, Long> {
}
