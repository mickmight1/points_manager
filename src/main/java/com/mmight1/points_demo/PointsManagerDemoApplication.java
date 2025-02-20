package com.mmight1.points_demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.mmight1.points_demo.model")
public class PointsManagerDemoApplication implements CommandLineRunner {

	// TOD implement REST interface
	
	/*
	 * @Autowired private PointsManagerRepository repository;
	 */

	public static void main(String[] args) {
		SpringApplication.run(PointsManagerDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// the main application shall expose a rest interface where it will
		// support GET points balance requests, and spend points requests (POSTs).

		// Save a new test entity
		/*
		 * PointsManager entity = new PointsManager("Accounts Z");
		 * repository.save(entity);
		 */

		// Fetch all test entities from the database
		/*
		 * List<PointsManager> entities = repository.findAll();
		 * System.out.println("Number of Accounts in the database: " + entities.size());
		 * entities.forEach(e -> System.out.println("Id: " + e.getId() + ", account: " +
		 * e.getAccount() + ", balance: " + e.getBalance()));
		 */
	}
}
