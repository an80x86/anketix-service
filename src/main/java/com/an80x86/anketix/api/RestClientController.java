package com.an80x86.anketix.api;

import com.an80x86.anketix.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestClientController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String webUrl = "https://earsivportal.efatura.gov.tr/earsiv-services/";

	@PostMapping(path = "/login")
	public ResponseEntity<String> setLogin(@RequestBody LoginDto loginDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("assoscmd", "anologin");
		map.add("rtype", "json");
		map.add("userid", loginDto.getUsername());
		map.add("sifre", loginDto.getUserpass());
		map.add("sifre2", loginDto.getUserpass());
		map.add("parola", "1");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = restTemplate.postForEntity( webUrl + "assos-login", request , String.class );

		return response;
	}

}
