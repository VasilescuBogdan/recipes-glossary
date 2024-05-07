package com.vasilescu.bogdan.server.repository;

import com.vasilescu.bogdan.server.model.Author;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface AuthorRepository extends Neo4jRepository<Author, String> {
}
