package com.example.security.resources;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class TestResource {
	@Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping("/solo-autenticados")
	@SecurityRequirement(name = "bearerAuth")
	public String get(@Parameter(hidden = true) @RequestHeader String authorization, Principal principal) {
		return "El usuario está autenticado\n  Usuario:" + principal.getName() + "\n  Authorization: " + authorization;
	}
	
	@GetMapping("/solo-admin")
	@SecurityRequirement(name = "bearerAuth")
	public String getAdmin() {
		return "El usuario es administrador";
	}
	
	@GetMapping("/password/encode")
	@SecurityRequirement(name = "bearerAuth")
	public String getPass(String pass) {
		return passwordEncoder.encode(pass);
	}
	@GetMapping("/password/validate")
	@SecurityRequirement(name = "bearerAuth")
	public String getVal(String pass, String cmp) {
		return passwordEncoder.matches(pass, cmp) ? "OK":"KO";
	}
	
	@Value("${jwt.secret}")
	private String SECRET;
	
	@GetMapping("/secreto")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	public String getSecreto() {
		return SECRET;
	}
}
