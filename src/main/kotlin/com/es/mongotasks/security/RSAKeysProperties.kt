package com.es.mongotasks.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@Component
@ConfigurationProperties(prefix = "rsa")
data class RSAKeysProperties(
    var publicKey : RSAPublicKey,
    var privateKey: RSAPrivateKey
)