package com.example.integration.repository;

import com.example.integration.repository.api.TapDispatcherSyncDateRepository;
import com.example.integration.repository.config.IntegrationContextConfiguration;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = IntegrationContextConfiguration.class)
@TestPropertySource(locations = "classpath:application.properties")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class TapDispatcherSyncDateRepositoryTest {

    @Autowired
    private TapDispatcherSyncDateRepository tapDispatcherSyncDateRepository;

    @Test
    @DatabaseSetup(value = "/datasets/lg_rods_tap_sync_time.xml")
    public void findAndLock() throws Exception {
        LocalDateTime lastSyncDate = LocalDateTime.ofInstant(tapDispatcherSyncDateRepository.findAndLock().toInstant(),
                ZoneId.systemDefault());
        Assert.assertEquals(LocalDateTime.parse("2017-01-01T16:56:00"),lastSyncDate);
    }

    @Test
    @DatabaseSetup(value = "/datasets/lg_rods_tap_sync_time.xml")
    public void setNewDate() throws Exception {
        Date currentSyncDate = new Date();
        tapDispatcherSyncDateRepository.setNewDate(currentSyncDate);
        Date lastSyncDate = tapDispatcherSyncDateRepository.findAndLock();
        Assert.assertEquals(currentSyncDate.toString(),lastSyncDate.toString());
    }

}