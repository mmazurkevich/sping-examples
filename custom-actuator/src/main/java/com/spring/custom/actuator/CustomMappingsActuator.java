package com.spring.custom.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikhail on 25.03.2017.
 */
@Component
@Conditional(ConditionalOnProfile.class)
public class CustomMappingsActuator extends AbstractEndpoint<List<String>> {

    @Autowired
    private List<Endpoint> endpoints;

    public CustomMappingsActuator() {
        super("custommapping");
    }

    @Override
    public List<String> invoke() {
        List<String> mappingList = new ArrayList<>();
        endpoints.forEach(endpoint -> mappingList.add(endpoint.getId()));
        return mappingList;
    }
}
