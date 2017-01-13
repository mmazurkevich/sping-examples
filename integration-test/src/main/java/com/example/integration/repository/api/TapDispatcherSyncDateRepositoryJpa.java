package com.example.integration.repository.api;

import com.example.integration.repository.entity.TapDispatcherSyncDateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

public interface TapDispatcherSyncDateRepositoryJpa extends JpaRepository<TapDispatcherSyncDateEntity,String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    TapDispatcherSyncDateEntity findOne(String id);
}
