package masterMindSolver

import kotlin.test.Test
import kotlin.test.assertFailsWith

/*
class MasterMindSolverTest {
    @Test
    fun testA() {
        val codeLength = 4
        val codeItems = listOf("Blau", "Rot", "Grün", "Gelb", "Weiß", "Braun")
        val goalIdx = Code(codeLength) { codeItems.indices.random() }
        //val goal = goalIdx.map { codeItems[it] }
        solveMasterMind(
            codeLength,
            codeItems
        ) {
            val response = guessIdx.match(goalIdx)
            println(s.size)
            println(it)
            println(response)
            println()
            response
        }
    }

    @Test
    fun testA_5Times() {
        repeat(5) { testA() }
    }

    @Test
    fun testB() {
        val codeLength = 5
        val codeItems = ('A'..'I').map { it.toString() }
        val goalIdx = Code(codeLength) { codeItems.indices.random() }
        //val goal = goalIdx.map { codeItems[it] }
        solveMasterMind(
            codeLength,
            codeItems
        ) {
            val response = guessIdx.match(goalIdx)
            println(s.size)
            println(it)
            println(response)
            println()
            response
        }
    }

    @Test
    fun testWrongResponse() {
        val codeLength = 4
        val codeItems = listOf("Blau", "Rot", "Grün", "Gelb", "Weiß", "Braun")
        assertFailsWith<IllegalStateException> {
            solveMasterMind(
                codeLength,
                codeItems
            ) {
                val response = Response(1, 2)
                println(s.size)
                println(it)
                println(response)
                println()
                response
            }
        }
    }

    @Test
    fun testIllegalResponse() {
        val codeLength = 4
        val codeItems = listOf("Blau", "Rot", "Grün", "Gelb", "Weiß", "Braun")
        assertFailsWith<IllegalStateException> {
            solveMasterMind(
                codeLength,
                codeItems
            ) {
                val response = Response(3, 2)
                println(s.size)
                println(it)
                println(response)
                println()
                response
            }
        }
    }
}

 */