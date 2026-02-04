package com.prx.commons.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CrudServiceTest {

    private final CrudService<Long, String> crudService = new CrudService<>() {};

    @Test
    @DisplayName("Create entity returns not implemented")
    void createEntityReturnsNotImplemented() {
        ResponseEntity<String> response = crudService.create("entity");
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }

    @Test
    @DisplayName("Update entity returns not implemented")
    void updateEntityReturnsNotImplemented() {
        ResponseEntity<String> response = crudService.update(1L, "entity");
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }

    @Test
    @DisplayName("Delete entity returns not implemented")
    void deleteEntityReturnsNotImplemented() {
        ResponseEntity<String> response = crudService.delete(1L, "entity");
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }

    @Test
    @DisplayName("Find entity returns not implemented")
    void findEntityReturnsNotImplemented() {
        ResponseEntity<String> response = crudService.find(1L);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }

    @Test
    @DisplayName("List entities returns not implemented")
    void listEntitiesReturnsNotImplemented() {
        ResponseEntity<List<String>> response = crudService.list(1L, 2L);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }
}
