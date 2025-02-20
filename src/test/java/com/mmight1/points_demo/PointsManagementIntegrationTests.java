package com.mmight1.points_demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mmight1.points_demo.exception.InsufficientPointsException;
import com.mmight1.points_demo.model.Accounts;
import com.mmight1.points_demo.repository.AccountsRepository;
import com.mmight1.points_demo.service.PointsService;

@SpringBootTest
public class PointsManagementIntegrationTests {

	@Autowired
	private AccountsRepository accountsRepo;
	/*@Autowired
	private IdempotencyKeyRepository IdmpKeysRepo;*/
	@Autowired
	private PointsService pointsService;

	private static final String ACCOUNT_A_NAME = "AccountA";
	private static final String ACCOUNT_B_NAME = "AccountB";

	private static final Long ACCOUNT_A_INITIAL_BALANCE = 100L;
	private static final Long ACCOUNT_B_INITIAL_BALANCE = 0L;

	Accounts entityA;
	Accounts entityB;

	Accounts savedEntityA;
	Accounts savedEntityB;

	@BeforeEach
	public void setup() {
		// Create two new Accounts and set their initial balances
		entityA = new Accounts(ACCOUNT_A_NAME, ACCOUNT_A_INITIAL_BALANCE);
		savedEntityA = accountsRepo.save(entityA);
		// check we can search by Id
		assertNotNull(savedEntityA.getId(), "Saved entity should have an id assigned");

		entityB = new Accounts(ACCOUNT_B_NAME);
		savedEntityB = accountsRepo.save(entityB);
		// assert the correct balance is being set
		assertEquals(ACCOUNT_B_INITIAL_BALANCE, savedEntityB.getBalance(),
				"Expected balance of Accounts: " + savedEntityB.getAccount() + " to be: " + ACCOUNT_B_INITIAL_BALANCE
						+ "but it was " + savedEntityB.getBalance() + ".");
	}

	@Test
	public void testSpendingValidConditions() {
		// Spend a valid amount of points (60) from Accounts A who has initial balance
		// of 100 points
		String uuidAccountA = UUID.randomUUID().toString();
		Accounts updatedAccountA = pointsService.spendPoints(uuidAccountA, savedEntityA.getId(), 60L);
		assertEquals(40L, updatedAccountA.getBalance());
	}

	@Test
	public void testSpendingMoreThanAvailableBalance() {
		// Attempt to spend an invalid amount of points (200) from Accounts B who has
		// initial balance of 75 points
		String uuidAccountB = UUID.randomUUID().toString();
		assertThrows(InsufficientPointsException.class, () -> {
			pointsService.spendPoints(uuidAccountB, savedEntityB.getId(), 200L);
		}, "Expected an InsufficientPointsException when trying to spend more than available balance.");
	}

	// Attempt to spend an invalid amount of points (41) from Accounts B who has
	// current balance of 40 points

	@AfterEach
	public void tearDown() {
		accountsRepo.delete(savedEntityA);
		accountsRepo.delete(savedEntityB);

		// Verify the test entities no longer exists
		Optional<Accounts> deletedEntity = accountsRepo.findById(savedEntityA.getId());
		assertFalse(deletedEntity.isPresent(), "Entity A should have been deleted from the database.");

		deletedEntity = accountsRepo.findById(savedEntityB.getId());
		assertFalse(deletedEntity.isPresent(), "Entity B should have been deleted from the database.");
	}
}
