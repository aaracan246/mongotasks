package com.es.mongotasks.controller

import com.es.mongotasks.error.exception.BadRequestException
import com.es.mongotasks.error.exception.ForbiddenException
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

        if (newTarea.titulo.isEmpty() && newTarea.status == null && newTarea.usuario == null){
            throw BadRequestException("No field can be empty.")
        }


        if (userRoleCheck != "ROLE_ADMIN" && newTarea.usuario != usernameToCheck){
            val insertTarea = tareaService.insertTarea(newTarea)
            return ResponseEntity(insertTarea, HttpStatus.CREATED)
        }
        else return null
    }

    @GetMapping()
    fun getAllTareas(
        @RequestHeader("Authorization") token: String
    ): List<Tarea>{
        val usernameToCheck = tokenService.extractorUsername(token)
        val userRoleCheck = tokenService.extractorRoles(token)
        val listaTareasTotal = tareaService.findAll()
        var listaTareasUser: MutableList<Tarea> = mutableListOf()

        if (userRoleCheck == "ROLE_ADMIN") {
            return listaTareasTotal
           }

        listaTareasUser = listaTareasTotal.filter { it.usuario == usernameToCheck }.toMutableList()
        return listaTareasUser
    }

    //____________________________________________________________________________________________

    @PutMapping("/update_tarea/{id}")   // No es necesaria para la entrega
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

    //____________________________________________________________________________________________

    @PutMapping("/complete_tarea/{id}")
    fun completeTarea(
        @PathVariable id: String,
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<Tarea> {
        val usernameToCheck = tokenService.extractorUsername(token)
        val userRoleCheck = tokenService.extractorRoles(token)
        val existingTarea = tareaService.findById(id) ?: throw NotFoundException("Tarea not found.")


        println(userRoleCheck) // depurasao
        val updatedTask = existingTarea.copy(status = false) // Solo cambiamos el estado, el resto igual
        if (userRoleCheck != "ROLE_ADMIN" && updatedTask.usuario != usernameToCheck){
            throw ForbiddenException("You do not have the required role.")
        }

        val savedTarea = tareaService.updateTarea(updatedTask)
        return ResponseEntity(savedTarea, HttpStatus.OK)
    }

    @DeleteMapping("/delete_tarea/{id}")
    fun deleteTarea(
        @PathVariable id: String,
        @RequestHeader("Authorization")
        token: String): ResponseEntity<String>
    {
        val usernameToCheck = tokenService.extractorUsername(token)
        val userRoleCheck = tokenService.extractorRoles(token)
        val existingTarea = tareaService.findById(id) ?: throw NotFoundException("Tarea not found.")

        if (userRoleCheck != "ROLE_ADMIN" && existingTarea.usuario != usernameToCheck){
            throw ForbiddenException("You do not have the required role.")
        }

        tareaService.delete(existingTarea)

        return ResponseEntity("Task deleted successfully!", HttpStatus.OK)
    }
}