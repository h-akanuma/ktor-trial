package com.example.user.route

import com.example.shared.valueobject.UserId
import com.example.user.controller.UserController
import com.example.user.form.CreateUserForm
import com.example.user.form.UpdateUserForm
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.request.*
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoute() {
    val userController by inject<UserController>()

    @Resource("/users/{id}")
    data class ShowUser(val id: Long)
    get<ShowUser> { param ->
        val user = userController.showUser(UserId(param.id))
        call.respond(user)
    }

    @Resource("/users")
    class CreateUser
    post<CreateUser> {
        val form = call.receive<CreateUserForm>()
        val user = userController.createUser(form)
        call.respond(user)
    }

    @Resource("/users/{id}")
    data class UpdateUser(val id: Long)
    put<UpdateUser> { param ->
        val form = call.receive<UpdateUserForm>()
        userController.updateUser(UserId(param.id), form)
        call.respond(HttpStatusCode.OK)
    }

    @Resource("/users/{id}")
    data class DeleteUser(val id: Long)
    delete<DeleteUser> { param ->
        userController.deleteUser(UserId(param.id))
        call.respond(HttpStatusCode.OK)
    }
}