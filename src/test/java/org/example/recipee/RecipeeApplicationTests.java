package org.example.recipee;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = RecipeeApplication.class)
@ActiveProfiles("test")
class RecipeeApplicationTests {

	@Test
	void contextLoads() {
	}

}
