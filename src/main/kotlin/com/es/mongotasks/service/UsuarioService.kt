package com.es.mongotasks.service


import com.es.mongotasks.dto.UsuarioDTO
import com.es.mongotasks.dto.UsuarioRegisterDTO
import com.es.mongotasks.error.exception.BadRequestException
import com.es.mongotasks.error.exception.NotFoundException
import com.es.mongotasks.model.Usuario
import com.es.mongotasks.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsuarioService : UserDetailsService {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    private lateinit var externalApiService: ExternalApiService


    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario: Usuario = usuarioRepository
            .findByUsername(username!!)
            .orElseThrow {
                NotFoundException("$username no existente")
            }

        return User.builder()
            .username(usuario.username)
            .password(usuario.password)
            .roles(usuario.roles)
            .build()
    }

    fun insertUser(usuarioInsertadoDTO: UsuarioRegisterDTO) : UsuarioDTO {

        // COMPROBACIONES
        // Comprobar si los campos vienen vacíos
        if (usuarioInsertadoDTO.username.isBlank()
            || usuarioInsertadoDTO.password.isBlank()
            || usuarioInsertadoDTO.passwordRepeat.isBlank()) {

            throw BadRequestException("Uno o más campos vacíos")
        }

        // Fran ha comprobado que el usuario existe previamente
        if(usuarioRepository.findByUsername(usuarioInsertadoDTO.username).isPresent) {
            throw Exception("Usuario ${usuarioInsertadoDTO.username} ya está registrado")
        }

        // comprobar que ambas passwords sean iguales
        if(usuarioInsertadoDTO.password != usuarioInsertadoDTO.passwordRepeat) {
            throw BadRequestException("Las contrasenias no coinciden")
        }

        // Comprobar el ROL
        if(usuarioInsertadoDTO.rol != null && usuarioInsertadoDTO.rol != "USER" && usuarioInsertadoDTO.rol != "ADMIN" ) {
            throw BadRequestException("ROL: ${usuarioInsertadoDTO.rol} incorrecto")
        }


        // Comprobar la provincia
        val datosProvincias = externalApiService.obtenerProvinciasDesdeApi()
        var cpro: String = ""
        if(datosProvincias != null) {
            if(datosProvincias.data != null) {
                val provinciaEncontrada = datosProvincias.data.stream().filter {
                    it.PRO == usuarioInsertadoDTO.direccion.provincia.uppercase()
                }.findFirst().orElseThrow {
                    BadRequestException("Provincia ${usuarioInsertadoDTO.direccion.provincia} no encontrada")
                }
                cpro = provinciaEncontrada.CPRO
            }
        }

        // Comprobar el municipio
        val datosMunicipios = externalApiService.obtenerMunicipiosDesdeApi(cpro)
        if(datosMunicipios != null) {
            if(datosMunicipios.data != null) {
                datosMunicipios.data.stream().filter {
                    it.DMUN50 == usuarioInsertadoDTO.direccion.municipio.uppercase()
                }.findFirst().orElseThrow {
                    BadRequestException("Municipio ${usuarioInsertadoDTO.direccion.municipio} incorrecto")
                }
            }
        }

        // Insertar el user (convierto a Entity)
        val usuario = Usuario(
            null,
            usuarioInsertadoDTO.username,
            passwordEncoder.encode(usuarioInsertadoDTO.password),
            usuarioInsertadoDTO.email,
            usuarioInsertadoDTO.direccion
        )

        // inserto en bd
        usuarioRepository.insert(usuario)

        // retorno un DTO
        return UsuarioDTO(
            usuario.username,
            usuario.roles,
            usuario.direccion
        )

    }
}