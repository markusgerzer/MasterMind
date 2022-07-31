package masterMindSolver

import kotlin.test.Test
import kotlin.test.assertTrue

/*
class IntMasterMindSolverTest {
    private fun runTest(
        goal: Code<Int>,
        length: Int = 4,
        base: Int = 6
    ) {
        var count = 0
        IntMasterMindSolver(length, base) {
            count++
            //assert(count <= 5)
            val response = it.match(goal)
            println(s)
            println(it)
            println(response)
            println(count)
            println()
            response
        }
        println("----------------------------------------------")
    }

    @Test
    fun test1() {
        runTest(Code(listOf(4, 2, 0, 5)))
        runTest(Code(listOf(3, 1, 0, 3)))
    }

    @Test
    fun randomTest1() {
        val length = 4
        val base = 8
        val range = 0 until base
        val goal = Code(length) { range.random() }
        runTest(goal, length, base)
    }

    @Test
    fun randomTest2() {
        val length = 4
        val base = 10
        val range = 0 until base
        val goal = Code(length) { range.random() }
        runTest(goal, length, base)
    }

    @Test
    fun length5RandomTest() {
        val length = 5
        val base = 3
        val range = 0 until base
        val goal = Code(length) { range.random() }
        runTest(goal, length, base)
    }

    @Test
    fun testMatchResponse() {
        val length = 4
        val base = 6
        val range = 0 until base
        val goal = Code(length) { range.random() }

        var count = 0
        IntMasterMindSolver(length, base) {
            count++
            val response = it.match(goal)

            for (code1 in unusedCodes) {
                for (response1 in allResponses) {
                    for (code2 in unusedCodes) {
                        val response2 = code1.match(code2)
                        assertTrue(code1.matchResponse(code2, response2))
                    }
                }
            }

            println(it)
            println(response)
            println(count)
            println()
            response
        }
    }
}

 */