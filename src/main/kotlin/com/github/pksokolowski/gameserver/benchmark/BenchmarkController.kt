package com.github.pksokolowski.gameserver.benchmark

import com.github.pksokolowski.gameserver.benchmark.BenchmarkService.QueryData
import com.github.pksokolowski.gameserver.engine.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.lang.StringBuilder
import kotlin.system.measureTimeMillis

@RestController
class BenchmarkController(private val benchmarkService: BenchmarkService) {

    @GetMapping("/benchmark/{type}/{depth}")
    fun runIndividualQueries(
            @PathVariable("type") type: Int,
            @PathVariable("depth") depth: Int): String {
        val queryData = benchmarkService.queryPreparers[type].invoke(depth)
        val time = measureTimeMillis {
            val response = play(queryData.query)
        }
        return "${queryData.info} took $time ms"
    }

    @GetMapping("/benchmark/{untilType}")
    fun runBenchmark(@PathVariable("untilType") untilType: Int =0): String {
        var totalTime = 0L
        val partialResults = mutableListOf<String>()
        val queriesRan = mutableListOf<QueryData>()

        for (i in benchmarkService.queryPreparers.indices) {
            if (untilType > 0 && i == untilType) break
            val queryData = benchmarkService.queryPreparers[i].callBy(emptyMap())
            queriesRan.add(queryData)

            val time = measureTimeMillis {
                val response = play(queryData.query)
            }
            totalTime += time
            partialResults.add("${queryData.info} took $time ms")
        }


        val sb = StringBuilder()
        sb.append("<body bgcolor='black' text='white' link='green' vlink='green'/>" )
        sb.append("<h1>Total time: $totalTime ms</H1>")

        val benchmarkLegacyMapping = "/benchmark/${queriesRan.size}"
        sb.append("""
            This was equvalent to: $benchmarkLegacyMapping <br>
            /benchmark/0 runs all benchmarks, use larger number in order to restrict benchmark to a legacy set of n types<br><br>
        """.trimIndent())

        for ((i, it) in partialResults.withIndex()) {
            val depth = queriesRan[i].query.depthAllowed
            sb.append("<a href ='/benchmark/$i/$depth'> $it </a><br>")
        }

        return sb.toString()
    }
}