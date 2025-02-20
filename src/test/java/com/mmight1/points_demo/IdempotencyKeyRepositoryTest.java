package com.mmight1.points_demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mmight1.points_demo.model.IdempotencyKey;
import com.mmight1.points_demo.repository.IdempotencyKeyRepository;

@SpringBootTest
public class IdempotencyKeyRepositoryTest {

	@Autowired
	private IdempotencyKeyRepository repository;

	@Test
	public void testInsertAndDelete() {
		String uuid= UUID.randomUUID().toString();
		
		// Create a new TestEntity
		IdempotencyKey entity = new IdempotencyKey(uuid, "Test Accounts Name");

		// Insert the entity into the database
		IdempotencyKey savedEntity = repository.save(entity);
		assertNotNull(savedEntity.getIdmpId(), "Saved entity should have an id assigned");

		// Retrieve the entity from the database and verify it exists
		Optional<IdempotencyKey> retrievedEntity = repository.findByIdempotencyKey(savedEntity.getIdempotencyKey());
		assertTrue(retrievedEntity.isPresent(), "Idempotency Key should be present in the database");
		assertEquals(uuid, retrievedEntity.get().getIdempotencyKey(),
				"Idempotency Key account names should match");

		// Delete the entity from the database
		repository.delete(savedEntity);
		// repository.flush();

		// Verify the entity no longer exists
		Optional<IdempotencyKey> deletedEntity = repository.findByIdempotencyKey(savedEntity.getIdempotencyKey());
		assertFalse(deletedEntity.isPresent(), "Entity should be deleted from the database");
	}
}
