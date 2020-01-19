package com.an80x86.anketix.api;

import com.an80x86.anketix.dto.DraftDto;
import com.an80x86.anketix.dto.LoginDto;
import com.an80x86.anketix.dto.TokenDto;
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

import java.text.SimpleDateFormat;
import java.util.Random;

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
		ResponseEntity<String> response = restTemplate.postForEntity( webUrl + "assos-login", request, String.class );

		return response;
	}

	@PostMapping(path = "/info")
	public ResponseEntity<String> setInformation(@RequestBody TokenDto token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("cmd", "EARSIV_PORTAL_KULLANICI_BILGILERI_GETIR");
		map.add("callid", getRandomString() + "-11");
		map.add("pageName", "RG_KULLANICI");
		map.add("token", token.getToken());
		map.add("jp", "{}");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = restTemplate.postForEntity( webUrl + "dispatch", request, String.class );

		return response;
	}

	@PostMapping(path = "/draft")
	public ResponseEntity<String> setDraft(@RequestBody DraftDto draft) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("cmd", "EARSIV_PORTAL_TASLAKLARI_GETIR");
		map.add("callid", getRandomString() + "-11");
		map.add("pageName", "RG_BASITTASLAKLAR");
		map.add("token", draft.getToken());
		map.add("jp", "{\"baslangic\":\""+draft.getBeginDate()+"\",\"bitis\":\""+draft.getEndDate()+"\"}");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = restTemplate.postForEntity( webUrl + "dispatch", request, String.class );

		return response;
	}

	private String getRandomString() {

		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 13;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int)
					(random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();
		return generatedString;
	}
}
