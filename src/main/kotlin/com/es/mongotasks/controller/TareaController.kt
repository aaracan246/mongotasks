package com.es.mongotasks.controller

import com.es.mongotasks.service.TareaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tareas")
class TareaController {

    @Autowired
    private lateinit var tareaService: TareaService

    fun insertTarea(){}

    fun getAllTareas(){}

    fun updateTarea(){}

    fun completeTarea(){        // Es un update que simplemente cambia el boolean status a completado

    }

    fun deleteTarea(){}
}