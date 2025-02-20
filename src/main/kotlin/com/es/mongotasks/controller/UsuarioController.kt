package com.es.mongotasks.controller

import com.es.mongotasks.service.TokenService
import com.es.mongotasks.dto.LoginUsuarioDTO
import com.es.mongotasks.dto.UsuarioDTO
import com.es.mongotasks.dto.UsuarioRegisterDTO
import com.es.mongotasks.error.exception.NotAuthorizedException

import com.es.mongotasks.service.UsuarioService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.naming.AuthenticationException


@RestController
@RequestMapping("/usuarios")
class UsuarioController {


    @Autowired
    private lateinit var tokenService: TokenService
    @Autowired
    private lateinit var usuarioService: UsuarioService
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @PostMapping("/register")
    fun insert(
        httpRequest: HttpServletRequest,
        @RequestBody usuarioRegisterDTO: UsuarioRegisterDTO
    ) : ResponseEntity<UsuarioDTO> {

        if (usuarioRegisterDTO.username.isEmpty()){
            throw Exception("Username cannot be empty.")
        }
        else if (usuarioRegisterDTO.password.isEmpty()){
            throw Exception("Password cannot be empty.")
        }
        else if (usuarioRegisterDTO.passwordRepeat.isEmpty() && usuarioRegisterDTO.passwordRepeat != usuarioRegisterDTO.password ){
            throw Exception("PasswordR cannot be empty nor different than the previous one.")
        }
        else if (usuarioRegisterDTO.rol?.isEmpty() == true){
            throw Exception("Rol cannot be empty.")
        }


        val usuarioInsertadoDTO: UsuarioDTO = usuarioService.insertUser(usuarioRegisterDTO)

        return ResponseEntity(usuarioInsertadoDTO, HttpStatus.CREATED)
    }

    @PostMapping("/login")
    fun login(@RequestBody usuario: LoginUsuarioDTO) : ResponseEntity<Any>? {

        val authentication: Authentication
        try {
            authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(usuario.username, usuario.password))
        } catch (e: AuthenticationException) {
            throw NotAuthorizedException("Credenciales incorrectas")
        }

        // SI PASAMOS LA AUTENTICACIÓN, SIGNIFICA QUE ESTAMOS BIEN AUTENTICADOS
        // PASAMOS A GENERAR EL TOKEN
        var token = tokenService.generarToken(authentication)

        return ResponseEntity(mapOf("token" to token), HttpStatus.CREATED)
    }
}