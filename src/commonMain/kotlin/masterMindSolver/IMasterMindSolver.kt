package masterMindSolver

interface IMasterMindSolver<T> {
    val codeLength: Int
    val unusedCodes: List<Code<T>>
    val s: List<Code<T>>
    val guess: Code<T>
    val allResponses: List<Response>
    fun allCodes(): List<Code<T>>
    fun processNext(response: Response)

    fun Response.isSolved() = this == Response(codeLength, 0)
    fun Response.isNotSolved() = !isSolved()
}