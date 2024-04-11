package com.vasilescu.bogdan.server.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Data
public class DietType {
    @Id
    private String name;
}
