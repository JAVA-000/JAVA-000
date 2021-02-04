package com.gitee.ywj1352.router;

import java.util.List;

public interface HttpEndpointRouter {

    String route(List<String> endpoints);
    
}
