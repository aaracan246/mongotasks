package com.es.mongotasks.controller

import com.es.mongotasks.error.exception.BadRequestException
import com.es.mongotasks.error.exception.NotFoundException
import com.es.mongotasks.model.Tarea
import com.es.mongotasks.service.TareaService
import com.es.mongotasks.service.TokenService
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

    @Autowired
    private lateinit var tokenService: TokenService

    @PostMapping("/insert_tarea")
    fun insertTarea(
        @RequestHeader("Authorization") token: String,
        httpRequest: HttpServletRequest,
        @RequestBody newTarea: Tarea
    ): ResponseEntity<Tarea>?{
        val usernameToCheck = tokenService.extractorUsername(token)
        val userRoleCheck = tokenService.extractorRoles(token)

        if (newTarea.titulo.isEmpty() || newTarea.status == null || newTarea.usuario == null){
            throw BadRequestException("No field can be empty.")
        }


        if (newTarea.usuario == usernameToCheck || userRoleCheck == "ADMIN"){
            val insertTarea = tareaService.insertTarea(newTarea)
            return ResponseEntity(insertTarea, HttpStatus.CREATED)
        }
        else return null
    }

    @GetMapping()
    fun getAllTareas(): List<Tarea>{
        val lista = tareaService.findAll()

        return lista
    }

    @PutMapping("/update_tarea/{id}")
    fun updateTarea(
        @PathVariable id: String,
        @RequestBody updatedTarea: Tarea
    ): ResponseEntity<Tarea> {
        val existingTarea = tareaService.findById(id) ?: throw BadRequestException("Tarea not found.")

        val updated = existingTarea.copy(
            titulo = updatedTarea.titulo.ifEmpty { existingTarea.titulo },
            desc = updatedTarea.desc ?: existingTarea.desc,
            status = updatedTarea.status ?: existingTarea.status,
            usuario = updatedTarea.usuario ?: existingTarea.usuario
        )

        val savedTarea = tareaService.updateTarea(updated)
        return ResponseEntity(savedTarea, HttpStatus.OK)
    }

    @PutMapping("/complete_tarea/{id}")
    fun completeTarea(@PathVariable id: String): ResponseEntity<Tarea> {
        val existingTarea = tareaService.findById(id) ?: throw NotFoundException("Tarea not found.")

        val updated = existingTarea.copy(status = true)
        val savedTarea = tareaService.updateTarea(updated)
        return ResponseEntity(savedTarea, HttpStatus.OK)
    }

    @DeleteMapping("/delete_tarea/{id}")
    fun deleteTarea(@PathVariable id: String){
        val existingTarea = tareaService.findById(id) ?: throw NotFoundException("Tarea not found.")

        tareaService.delete(existingTarea)
        println("Tarea deleted successfully.")
    }
}