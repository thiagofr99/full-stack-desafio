package com.devthiagofurtado.fullstackchallenge;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("squid:S2699")
@SpringBootTest
class FullStackDesafioApplicationTests {

	@Test
	void contextLoads() {
		FullStackDesafioApplication.main(new String[]{
				"--spring.main.web-environment=false",
				"--spring.autoconfigure.exclude=blahblahblah",
				// Override any other environment properties according to your needs
		});
	}

}
