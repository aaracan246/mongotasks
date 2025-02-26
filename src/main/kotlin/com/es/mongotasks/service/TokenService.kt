package com.es.mongotasks.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.*

@Service
class TokenService {

    @Autowired
    private lateinit var jwtEncoder: JwtEncoder

    @Autowired
    private lateinit var jwtDecoder: JwtDecoder

    fun generarToken(authentication: Authentication) : String {


        val roles: String = authentication.authorities.joinToString(" ") { it.authority } // Contiene los roles del usuario

        val payload: JwtClaimsSet = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(Instant.now())
            .expiresAt(Date().toInstant().plus(Duration.ofHours(1)))
            .subject(authentication.name)
            .claim("roles", roles)
            .build()

        return jwtEncoder.encode(JwtEncoderParameters.from(payload )).tokenValue;
    }

    fun extractorUsername(token: String): String{  // Extractor del username
        return jwtDecoder.decode(token).subject
    }

    fun extractorRoles(token: String): String{   // Extractor del rol
        val decodedToken = jwtDecoder.decode(token)
        val rol = decodedToken.claims["roles"].toString()
        return rol
    }
}