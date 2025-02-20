package com.mmight1.points_demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mmight1.points_demo.model.Accounts;
import com.mmight1.points_demo.repository.AccountsRepository;

@SpringBootTest
public class AccountsRepositoryTest {

    @Autowired
    private AccountsRepository repository;

    @Test
    public void testInsertAndDelete() {
        // Create a new TestEntity
    	Accounts entity = new Accounts("Test Row");

        // Insert the entity into the database
    	Accounts savedEntity = repository.save(entity);
        assertNotNull(savedEntity.getId(), "Saved entity should have an id assigned");

        // Retrieve the entity from the database and verify it exists
        Optional<Accounts> retrievedEntity = repository.findById(savedEntity.getId());
        assertTrue(retrievedEntity.isPresent(), "Accounts should be present in the database");
        assertEquals("Test Row", retrievedEntity.get().getAccount(), "Accounts name should match");

        // Delete the entity from the database
        repository.delete(savedEntity);
        //repository.flush();

        // Verify the entity no longer exists
        Optional<Accounts> deletedEntity = repository.findById(savedEntity.getId());
        assertFalse(deletedEntity.isPresent(), "Entity should be deleted from the database");
    }
}
