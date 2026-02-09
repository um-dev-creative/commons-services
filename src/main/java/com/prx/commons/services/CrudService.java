/*
 *  @(#)CrudService.java
 *
 *  Copyright (c) Luis Antonio Mata Mata. All rights reserved.
 *
 *   All rights to this product are owned by Luis Antonio Mata Mata and may only
 *  be used under the terms of its associated license document. You may NOT
 *  copy, modify, sublicense, or distribute this source file or portions of
 *  it unless previously authorized in writing by Luis Antonio Mata Mata.
 *  In any event, this notice and the above copyright must always be included
 *  verbatim with this file.
 */

package com.prx.commons.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Interface for basic CRUD operations.
 * Provides default methods that return HTTP 501 Not Implemented by default.
 *
 * @param <A> the type of the identifier
 * @param <T> the entity type
 * @version 1.0.0
 */
public interface CrudService<A, T> {

    /**
     * Creates a new entity.
     *
     * @param t the entity to create
     * @return a ResponseEntity representing the created entity or a status
     */
    default ResponseEntity<T> create(T t) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * Updates an existing entity.
     *
     * @param id the ID of the entity to update
     * @param t  the entity with updated information
     * @return a ResponseEntity representing the updated entity or a status
     */
    default ResponseEntity<T> update(A id, T t) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * Deletes an entity.
     *
     * @param id the ID of the entity to delete
     * @param t  the entity to delete
     * @return a ResponseEntity representing the deleted entity or a status
     */
    default ResponseEntity<T> delete(A id, T t) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * Finds an entity by its ID.
     *
     * @param id the ID of the entity
     * @return a ResponseEntity representing the found entity or a status
     */
    default ResponseEntity<T> find(A id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * Lists entities by their IDs.
     *
     * @param id the IDs of the entities to list
     * @return a ResponseEntity containing a list of entities or a status
     */
    default ResponseEntity<List<T>> list(A... id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
