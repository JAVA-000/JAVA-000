package com.gitee.ywj1352.filter;

import com.gitee.ywj1352.core.pojo.Request;
import io.netty.channel.ChannelHandlerContext;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RouterHttpRequestFilter implements HttpRequestFilter,HttpEndpointRouter {
    @Override
    public void filter(Request fullRequest, ChannelHandlerContext ctx) {
        String uri = fullRequest.getRequest().uri();
        List<String> endpoints = findEndpoints(uri);
        assert endpoints != null;
        String url = route(endpoints);
        fullRequest.setUrl(url);
    }


    //
    private List<String> findEndpoints(String uri){
      if (uri.startsWith("/api")){
          return Arrays.asList("http://127.0.0.1:8088");
      }
      return null;
    }



    @Override
    public String route(List<String> endpoints) {
        Random random = new Random();
        int idx = random.nextInt(endpoints.size());
        return endpoints.get(idx);
    }
}
