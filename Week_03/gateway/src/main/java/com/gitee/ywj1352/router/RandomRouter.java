package com.gitee.ywj1352.router;

import java.util.List;
import java.util.Random;

public class RandomRouter implements HttpEndpointRouter{

    @Override
    public String route(List<String> endpoints) {
        Random random = new Random();
        int idx = random.nextInt(endpoints.size());
        return endpoints.get(idx);
    }
}
