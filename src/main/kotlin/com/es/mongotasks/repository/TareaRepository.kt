package com.es.mongotasks.repository

import com.es.mongotasks.model.Tarea
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TareaRepository: MongoRepository<Tarea, String> {
}