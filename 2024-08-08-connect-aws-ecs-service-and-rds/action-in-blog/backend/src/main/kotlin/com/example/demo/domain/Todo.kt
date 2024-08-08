package com.example.demo.domain

import com.example.demo.util.TimeProvider.now
import com.example.demo.util.TimeProvider.toStringFormat

data class Todo(
    val id: Long = 0,
    val title: String,
    val createdAt: String = toStringFormat(now())
)
