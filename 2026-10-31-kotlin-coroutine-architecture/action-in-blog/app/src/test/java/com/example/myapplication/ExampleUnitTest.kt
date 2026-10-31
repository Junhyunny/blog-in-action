package com.example.myapplication

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds


class ExampleUnitTest {

    @Test
    fun example_01() {
        val scope = CoroutineScope(
            SupervisorJob() + Default
        )

        val job1 = scope.launch {
            delay(1000.milliseconds)
        }

        val job2 = scope.launch {
            delay(2000.milliseconds)
        }
    }

    @Test
    fun example_02() = runTest {
        val scope = CoroutineScope(
            SupervisorJob() + Default
        )
        val job1 = scope.launch {
            try {
                delay(1000.milliseconds)
                println("Job 1 completed")
            } finally {
                println("Job 1 cancelled")
            }
        }
        val job2 = scope.launch {
            try {
                delay(2000.milliseconds)
                println("Job 2 completed")
            } finally {
                println("Job 2 cancelled")
            }
        }
        delay(500.milliseconds)

        scope.cancel()

        joinAll(job1, job2)
    }

    @Test
    fun example_03() {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("Error: $exception")
        }
        val context =
            SupervisorJob() +
                    Default +
                    CoroutineName("Worker") +
                    handler
        val scope = CoroutineScope(context)

        scope.launch {
            println("first job")
        }
        scope.launch {
            println("second job")
        }
    }

    @Test
    fun example_04() = runTest {
        val scope = CoroutineScope(
            SupervisorJob() +
                    Default +
                    CoroutineName("Worker")
        )

        val job1 = scope.launch {
            println("first job - ${coroutineContext[CoroutineName]?.name}")
        }
        val job2 = scope.launch(CoroutineName("SpecialWorker")) {
            println("second job - ${coroutineContext[CoroutineName]?.name}")
        }
        joinAll(job1, job2)
    }

    @Test
    fun example_05() = runBlocking {
        val parentJob = SupervisorJob()
        val scope = CoroutineScope(parentJob + Dispatchers.Default)

        val job1 = scope.launch {
            delay(500.milliseconds)
            throw RuntimeException("Job 1 failed")
        }

        val job2 = scope.launch {
            try {
                delay(1000.milliseconds)
                println("Job 2 completed")
            } finally {
                if (!isActive) {
                    println("Job 2 cancelled")
                }
            }
        }

        joinAll(job1, job2)
        println("Parent active: ${parentJob.isActive}")
    }

    @Test
    fun example_06() = runBlocking {
        val parentJob = Job()
        val scope = CoroutineScope(parentJob + Dispatchers.Default)

        val job1 = scope.launch {
            delay(500.milliseconds)
            throw RuntimeException("Job 1 failed")
        }

        val job2 = scope.launch {
            try {
                delay(1000.milliseconds)
                println("Job 2 completed")
            } finally {
                if (!isActive) {
                    println("Job 2 cancelled")
                }
            }
        }

        joinAll(job1, job2)
        println("Parent active: ${parentJob.isActive}")
    }
}