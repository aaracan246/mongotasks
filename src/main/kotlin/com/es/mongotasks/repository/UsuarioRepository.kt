package com.es.mongotasks.repository

import com.es.mongotasks.model.Usuario
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository: MongoRepository<Usuario, String> {
    fun findByUsername(username: String) : Optional<Usuario>
}