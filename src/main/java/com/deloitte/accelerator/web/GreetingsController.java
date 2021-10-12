package com.deloitte.accelerator.web;

import com.deloitte.accelerator.web.exception.ResourceNotFoundException;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Api(tags = {"Greetings"})
@SwaggerDefinition(tags = {
		@Tag(name = "Greetings")
})
@RestController
@Slf4j
public class GreetingsController {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${weather.app.url}")
	private String baseUrl;

	@Value("${weather.app.key}")
	private String key;

	@ApiOperation(value = "Get Greetings by name", notes = "greetings by name", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200,
					message = "Greetings response by name"),
			@ApiResponse(code = 401,
					message = "Security Error, unauthenticated"),
			@ApiResponse(code = 403,
					message = "Security Error, Request lacks valid authentication credentials."),
			@ApiResponse(code = 500, message = "Server Error, Unknown Server Error."),
			@ApiResponse(code = 503,
					message = "Availability Error")})
	@GetMapping(value = "/api/v1/greetings")
	public ResponseEntity<String> get(@RequestParam String name) {
		return ResponseEntity.ok("Hello "+name);
	}


	@ApiOperation(value = "Get Weather by city by RestTemplate")
	@ApiResponses(value = {
			@ApiResponse(code = 200,
					message = "Weather by City"),
			@ApiResponse(code = 401,
					message = "Security Error, unauthenticated"),
			@ApiResponse(code = 403,
					message = "Security Error, Request lacks valid authentication credentials."),
			@ApiResponse(code = 500, message = "Server Error, Unknown Server Error."),
			@ApiResponse(code = 503,
					message = "Availability Error")})
	@GetMapping(value = "/api/v1/weather")
	public ResponseEntity<String> weather(@RequestParam(required = true) String city) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("q", city);
		params.add("appid", key);
		URI uri =  UriComponentsBuilder.fromHttpUrl(baseUrl)
				.queryParams(params).build().toUri();
		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.add("Accept-Type", "application/json");
		HttpEntity entity =  new HttpEntity<>("", httpHeaders);
		return restTemplate.exchange(uri, HttpMethod.GET,entity,String.class);
	}



}
