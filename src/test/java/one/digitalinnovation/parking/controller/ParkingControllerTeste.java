package one.digitalinnovation.parking.controller;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import one.digitalinnovation.parking.dto.ParkingCreateDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkingControllerTeste extends AbstractContainerBase {
	
	@LocalServerPort
	private int randomPort;
	
	@BeforeEach
	public void setUpTest() {
		System.out.println(randomPort);
		RestAssured.port = randomPort;
	}
	
	@Test
	void whenFindAllThenCheckResult() {

		 RestAssured.given()
         .auth().basic("user", "12345")
         .when()
         .get("/parking")
         .then()
         .statusCode(HttpStatus.OK.value());
	}

	
	@Test
	void whenCreateThenCheckIsCreated() {
		var createDTO = new ParkingCreateDTO();
		createDTO.setColor("AMARELO");
		createDTO.setLicense("ALE 7777");
		createDTO.setModel("Jetta");
		createDTO.setState("PE");
		
		RestAssured.given()
		.when()
		.contentType(MediaType.APPLICATION_JSON_VALUE)
		.body(createDTO)
		.post("/parking")
		.then()
		.statusCode(HttpStatus.CREATED.value())
		.body("license", Matchers.equalTo("ALE 7777")); //verificar o que esperar da api num teste automatizado
	}

}
