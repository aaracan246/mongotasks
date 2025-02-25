package com.es.mongotasks.service

import com.es.mongotasks.model.Tarea
import com.es.mongotasks.repository.TareaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TareaService {

    @Autowired
    private lateinit var tareaRepository: TareaRepository

    fun insertTarea(tarea: Tarea): Tarea{
        return tareaRepository.save(tarea)
    }

    fun findAll(): List<Tarea> {
        val listaTarea = tareaRepository.findAll()

        return listaTarea
    }


}