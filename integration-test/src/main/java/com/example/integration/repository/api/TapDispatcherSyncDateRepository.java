package com.example.integration.repository.api;

import java.util.Date;

public interface TapDispatcherSyncDateRepository {

    /**
     * Find latest sync date and lock.
     *
     * @return last sync date.
     */
    Date findAndLock();

    /**
     * Set new sync date.
     *
     * @param newDate newDate
     */
    void setNewDate(Date newDate);
}
