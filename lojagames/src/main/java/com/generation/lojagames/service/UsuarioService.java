package com.generation.lojagames.service;

import java.nio.charset.Charset; // IMPORTANTE
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64; //IMPORTANTE
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.lojagames.model.Usuario;
import com.generation.lojagames.model.UsuarioLogin;
import com.generation.lojagames.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired  // autorização
	private UsuarioRepository usuarioRepository;
	
	public Optional<Usuario> cadastrarUsuario(Usuario usuario){
		
		// metodo pra saber se o usuario existe ou não
		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())    // Bad Request = erro 400
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O Usuário já existe", null);  
		
		//instruçao para trazer a resposta (idade) em anos
		int idade = Period.between(usuario.getDataNascimento(), LocalDate.now()).getYears(); 
			if(idade <18)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário é menor de idade!", null);
		
		// criando objeto Encoder, ele que faz a criptografia
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		// grava e faz a substituica da senha normal pela senha criptografada
		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);
		
		return Optional.of(usuarioRepository.save(usuario));
	}
	
	public Optional<Usuario> atualizarUsuario(Usuario usuario){
		
		// metodo pra saber se o usuario existe ou não
		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) {    // Bad Request = erro 400
		
			//instruçao para trazer a resposta (idade) em anos
			int idade = Period.between(usuario.getDataNascimento(), LocalDate.now()).getYears(); 
				if(idade < 18)
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário é menor de idade!", null);
			
		// criando objeto Encoder, ele que faz a criptografia
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		// grava e faz a substituica da senha normal pela senha criptografada
		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);
		
		return Optional.of(usuarioRepository.save(usuario));
		
		} else{
		
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O Usuário não encontrado", null);  
		}
	}
	
		public Optional<UsuarioLogin> loginUsuario(Optional <UsuarioLogin> usuarioLogin){
			
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			Optional <Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());
			
			// vendo se o usuario existe
			if (usuario.isPresent()) {
				if(encoder.matches(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {
				
					// converter tudo em ascii e modificar para base64
					// sempre deixar "::" e espaço depois do basic, caso contrario irá dar erro
					String auth = usuarioLogin.get().getUsuario() + ":" + usuarioLogin.get().getSenha();
					byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
					String authHeader = "Basic " + new String(encodedAuth);
					
					usuarioLogin.get().setId(usuario.get().getId());
					usuarioLogin.get().setNome(usuario.get().getNome());
					usuarioLogin.get().setSenha(usuario.get().getSenha());
					usuarioLogin.get().setToken(authHeader);

					
					return usuarioLogin;
			}
		}
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "O Usuário ou Senha inválidos", null);  	
	}
}
