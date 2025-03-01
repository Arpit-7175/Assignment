//package com.networknt.petstore.model;
//
//import com.networknt.handler.HandlerProvider;
//import com.networknt.petstore.handler.userHandler.*;
//import io.undertow.Handlers;
//import io.undertow.server.HttpHandler;
//import io.undertow.util.Methods;
//
//public class PathHandlerProvider implements HandlerProvider {
//    @Override
//    public HttpHandler getHandler() {
//        return Handlers.routing()
//                .add(Methods.GET, "/v1/load", new UsersLoadGetHandler())
//                .add(Methods.GET, "/v1/users/{userId}", new UsersUserIdGetHandler())
//                .add(Methods.POST, "/v1/users", new UsersPostHandler())
//                .add(Methods.PUT, "/v1/users/{userId}", new UsersUserIdPutHandler())
//                .add(Methods.DELETE, "/v1/users/{userId}", new UsersUserIdDeleteHandler())
//                .add(Methods.DELETE, "/v1/users", new UsersDeleteHandler());
//    }
//}
