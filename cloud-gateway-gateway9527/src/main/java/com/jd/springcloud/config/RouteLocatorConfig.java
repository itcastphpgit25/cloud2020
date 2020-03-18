package com.jd.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteLocatorConfig {

    /**
     *
     * 配置一个id为route-name的路由规则
     * 当访问地址https://localhost:9527/guonei是会自动转发到地址https://news.baidu.com/guonei
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        final RouteLocatorBuilder.Builder routes = builder.routes();
        routes.route("path_route_jd",r->r.path("/guoji").uri("https://news.baidu.com")).build();
        return routes.build();
    }
}
