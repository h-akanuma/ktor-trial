package com.example

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime

object ObjectMapperBuilder {
    fun build(objectMapper: ObjectMapper = jacksonObjectMapper()): ObjectMapper {
        return objectMapper.apply {
            initializeBlock.invoke(this)
        }
    }

    val initializeBlock: ObjectMapper.() -> Unit = {
        configure(SerializationFeature.INDENT_OUTPUT, true)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        registerModule(KotlinModule.Builder().build())
        registerModule(
            JavaTimeModule()
                .addSerializer(LocalDate::class.java, LocalDateSerializer())
                .addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer())
        )
        registerModule(
            SimpleModule().apply {
            }
        )

        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        dateFormat = SimpleDateFormat("yyyy-MM-dd")
    }
}