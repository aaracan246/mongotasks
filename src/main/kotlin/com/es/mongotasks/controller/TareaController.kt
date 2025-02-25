package com.es.mongotasks.controller

import com.es.mongotasks.error.exception.BadRequestException
import com.es.mongotasks.model.Tarea
import com.es.mongotasks.service.TareaService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tareas")
class TareaController {

    @Autowired
    private lateinit var tareaService: TareaService

    @PostMapping("/insert_tarea")
    fun insertTarea(
        httpRequest: HttpServletRequest,
        @RequestBody newTarea: Tarea
    ): ResponseEntity<Tarea>{

        if (newTarea.titulo.isEmpty() || newTarea.status == null || newTarea.usuario == null){
            throw BadRequestException("No field can be empty.")
        }

        val insertTarea: Tarea = tareaService.insertTarea(newTarea)

        return ResponseEntity(insertTarea, HttpStatus.CREATED)
    }

    @GetMapping()
    fun getAllTareas(): List<Tarea>{
        val lista = tareaService.findAll()

        return lista
    }

    @PutMapping("/update_tarea/{id}")
    fun updateTarea(){}

    @PutMapping("/complete_tarea/{id}")
    fun completeTarea(){        // Es un update que simplemente cambia el boolean status a completado

    }

    @DeleteMapping("/delete_tarea/{id}")
    fun deleteTarea(){}
}