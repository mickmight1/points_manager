package com.mmight1.points_demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mmight1.points_demo.exception.InsufficientPointsException;
import com.mmight1.points_demo.model.Accounts;
import com.mmight1.points_demo.model.IdempotencyKey;
import com.mmight1.points_demo.repository.AccountsRepository;
import com.mmight1.points_demo.repository.IdempotencyKeyRepository;

@Service
public class PointsService {

	@Autowired
	private AccountsRepository accountsRepository;

	@Autowired
	private IdempotencyKeyRepository idempotencyKeyRepository;

	/**
	 * Deducts the given amount of points from the account.
	 * 
	 * @param idempotencyKeyUUID
	 * @param accountId          the ID of the account
	 * @param amount             the amount of points to spend
	 * 
	 * @return the updated Accounts after spending points
	 * @throws InsufficientPointsException if the account does not have enough
	 *                                     points
	 */
	@Transactional
	public Accounts spendPoints(String idempotencyKey, Long accountId, Long amount) {
		Accounts account = accountsRepository.findById(accountId)
				.orElseThrow(() -> new IllegalArgumentException("Accounts not found: " + accountId));

		// check for the existence of this idempotency key before performing the balance
		// operation
		Optional<IdempotencyKey> idmpKey = idempotencyKeyRepository.findByIdempotencyKey(idempotencyKey);
		if (!idmpKey.isEmpty()) {
			System.out.println("Request already seen, ignoring.");
			return account;
		} else {
			// add a new idempotency key to the table prior to performing any operations
			IdempotencyKey newKey = new IdempotencyKey(idempotencyKey, account.getAccount());
			idempotencyKeyRepository.save(newKey);

			if (account.getBalance() < amount) {
				throw new InsufficientPointsException(
						"Insufficient points. Current balance: " + account.getBalance() + ", requested: " + amount);
			}

			account.deductPoints(amount);
			return accountsRepository.save(account);
		}
	}
}
