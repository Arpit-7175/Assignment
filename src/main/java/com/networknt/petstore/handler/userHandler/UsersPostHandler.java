package com.networknt.petstore.handler.userHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.body.BodyHandler;
import com.networknt.config.Config;
import com.networknt.petstore.db.MongoStartupHookProvider;
import com.networknt.petstore.model.Address;
import com.networknt.petstore.model.User;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class UsersPostHandler implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        // Read the request body into a string asynchronously
        final Logger log = LoggerFactory.getLogger(UsersPostHandler.class);
        Map<String, String> body =
                (Map<String, String>) exchange.getAttachment(BodyHandler.REQUEST_BODY);
        User user= Config.getInstance().getMapper().convertValue(body, User.class);
            try {
                log.info("Received user: " + user.toString());

                // Generate a UUID for the user ID if not present in the request
                if (user.getId() == null) {
                    user.setId(UUID.randomUUID().toString());
                }

                // Insert the user into MongoDB
                MongoStartupHookProvider.db.getCollection("users", User.class).insertOne(user);
                log.info("User inserted into MongoDB: " + user);

                // Send a response after processing the user
                sendResponse(exchange, 201, "User created with id:"+user.getId()); // Send response with created user

            } catch (Exception e) {
                log.error("Error handling the request: " + e.getMessage());
                sendResponse(exchange, 500, "Internal Server Error: " + e.getMessage());
            }
        }

    private void sendResponse(HttpServerExchange exchange, int statusCode, Object responseBody) {
        // Ensure response is only sent once and set status code
        exchange.setStatusCode(statusCode);
        exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");

        try {
            // If responseBody is an object, serialize it to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String responseJson = objectMapper.writeValueAsString(responseBody);
            exchange.getResponseSender().send(responseJson);
        } catch (IOException e) {
            System.out.println("Error serializing response: " + e.getMessage());
            exchange.setStatusCode(500);
            exchange.getResponseSender().send("Internal Server Error: Response serialization failed");
        }
    }
}
