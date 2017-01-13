package com.example.integration.repository.impl;

import com.example.integration.repository.api.TapDispatcherSyncDateRepository;
import com.example.integration.repository.api.TapDispatcherSyncDateRepositoryJpa;
import com.example.integration.repository.entity.TapDispatcherSyncDateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class TapDispatcherSyncDateRepositoryImpl implements TapDispatcherSyncDateRepository {

    /**
     * Spring data implementation of TapDispatcherSyncDateRepository.
     */
    @Autowired
    private TapDispatcherSyncDateRepositoryJpa tapDispatcherSyncDateRepositoryJpa;

    /**
     * Predefined uuid for record.
     */
    private static final String SYNC_UUID = "1";

    @Override
    public Date findAndLock() {
        TapDispatcherSyncDateEntity tapDispatcherSyncDateEntity = tapDispatcherSyncDateRepositoryJpa.findOne(SYNC_UUID);
        if (tapDispatcherSyncDateEntity != null) {
            return tapDispatcherSyncDateEntity.getSyncDate();
        } else {
            return null;
        }
    }

    @Override
    public void setNewDate(Date newDate) {
        TapDispatcherSyncDateEntity tapDispatcherSyncDateEntity = tapDispatcherSyncDateRepositoryJpa.findOne(SYNC_UUID);
        if (tapDispatcherSyncDateEntity == null) {
            tapDispatcherSyncDateEntity = new TapDispatcherSyncDateEntity();
            tapDispatcherSyncDateEntity.setUuid(SYNC_UUID);
        }
        tapDispatcherSyncDateEntity.setSyncDate(newDate);
        tapDispatcherSyncDateRepositoryJpa.saveAndFlush(tapDispatcherSyncDateEntity);
    }
}
