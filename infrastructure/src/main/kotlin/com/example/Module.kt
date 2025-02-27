package com.example

import com.example.controller.HelloController
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object Module {
    fun modules() = module {
        singleOf(::HelloController)
    }
}