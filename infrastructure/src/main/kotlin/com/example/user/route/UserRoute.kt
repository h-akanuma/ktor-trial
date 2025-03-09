package com.example.user.route

import com.example.shared.valueobject.UserId
import com.example.user.controller.UserController
import com.example.user.form.CreateUserForm
import com.example.user.form.UpdateUserForm
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoute() {
    val userController by inject<UserController>()

//    @Resource("/users/{id}")
    get("/users/{id}") {
        val id = call.parameters["id"]?.toLong() ?: throw IllegalArgumentException("Invalid ID")
        val user = userController.showUser(UserId(id))
        call.respond(user)
    }

    post("/users") {
        val form = call.receive<CreateUserForm>()
        val user = userController.createUser(form)
        call.respond(user)
    }

    put("/users/{id}") {
        val id = call.parameters["id"]?.toLong() ?: throw IllegalArgumentException("Invalid ID")
        val form = call.receive<UpdateUserForm>()
        userController.updateUser(UserId(id), form)
        call.respond(HttpStatusCode.OK)
    }

    delete("/users/{id}") {
        val id = call.parameters["id"]?.toLong() ?: throw IllegalArgumentException("Invalid ID")
        userController.deleteUser(UserId(id))
        call.respond(HttpStatusCode.OK)
    }
}