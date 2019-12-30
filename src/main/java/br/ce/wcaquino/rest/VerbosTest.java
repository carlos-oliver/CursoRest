package br.ce.wcaquino.rest;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.restassured.RestAssured;


public class VerbosTest {
	@Test
	public void deveSalvarUsuario() {
		given()
			.log().all() //log 
			.contentType("application/json")
			.body("{\"name\": \"Jose\", \"age\": 50}")			
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Jose"))
			.body("age", is(50))
		;

	}

	
	@Test
	public void naoDeveSalvarUsuarioSemNome() {
		given()
			.log().all() //log 
			.contentType("application/json")
			.body("{\"age\": 50}")			
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(400)
			.body("id", is(nullValue()))
			.body("error", is("Name é um atributo obrigatório"))
		;

	}
	
	@Test
	public void deveAlterarUsuario() {
		given()
			.log().all() //log 
			.contentType("application/json")
			.body("{\"name\": \"Usuario alterado\", \"age\": 80}")			
		.when()
			.put("https://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuario alterado"))
			.body("age", is(80))
			.body("salary", is(1234.5678f))
		;

	}
	
	@Test
	public void devoCustomizarURLParte1() {
		given()
			.log().all() //log 
			.contentType("application/json")
			.body("{\"name\": \"Usuario alterado\", \"age\": 80}")			
		.when()
			.put("https://restapi.wcaquino.me/{entidade}/{userID}", "users", "1")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuario alterado"))
			.body("age", is(80))
			.body("salary", is(1234.5678f))
		;

	}
	
	@Test
	public void devoCustomizarURLParte2() {
		given()
			.pathParam("entidade", "users")
			.pathParam("userID", 1)
			.log().all() //log 
			.contentType("application/json")
			.body("{\"name\": \"Usuario alterado\", \"age\": 80}")			
		.when()
			.put("https://restapi.wcaquino.me/{entidade}/{userID}")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuario alterado"))
			.body("age", is(80))
			.body("salary", is(1234.5678f))
		;

	}
	
	@Test
	public void deveRemoverUsuario() {
		given()
		.log().all()
		.when()
			.delete("https://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(204)
			;
	}
	
	@Test
	public void naoDeveRemoverUsuarioInexistente() {
		given()
		.log().all()
		.when()
			.delete("https://restapi.wcaquino.me/users/100")
		.then()
			.log().all()
			.statusCode(400)
			.body("error", is("Registro inexistente"))
			;
	}
	
	@Test
	public void deveSalvarUsuarioUsandoMap() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "Usuario via map");
		params.put("age", 25);
		given()
			.log().all() //log 
			.contentType("application/json")
			.body(params)			
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Usuario via map"))
			.body("age", is(25))
		;

	}
	
	@Test
	public void deveSalvarUsuarioUsandoObjeto() {
		
		User user = new User("Usario via objeto", 35);
		given()
			.log().all() //log 
			.contentType("application/json")
			.body(user)			
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Usario via objeto"))
			.body("age", is(35))
		;

	}	
	
}
