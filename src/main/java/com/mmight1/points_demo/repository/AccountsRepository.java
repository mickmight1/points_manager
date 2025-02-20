package com.mmight1.points_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmight1.points_demo.model.Accounts;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    // Additional query methods (if needed) can be declared here.
}
