package com.networknt.petstore.db;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MongoConfig {
    String description;
    String host;
    String name;
    public String getHost() {
        return this.host;  // Make sure 'host' is a field in the MongoConfig class
    }

    public String getName() {
        return this.name;  // Make sure 'name' is a field in the MongoConfig class
    }
}

