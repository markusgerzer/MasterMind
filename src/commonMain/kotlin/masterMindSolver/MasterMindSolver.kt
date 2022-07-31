package masterMindSolver


/*suspend fun <T>solveMasterMind(
    codeLength: Int,
    codeItems: List<T>,
    responseFun: suspend MasterMindSolver<T>.(Code<T>) -> Response
): Code<T>? {
    var goal: Code<T>? = null
    IntMasterMindSolver(
        codeLength,
        codeItems.size
    ) {
        with(MasterMindSolver(codeItems, this)) {
            responseFun(guess).also {
                if (it.isSolved()) goal = guess
            }
        }
    }.process()
    return goal
}*/


class MasterMindSolver<T>(
    val codeItems: List<T>,
    private val intMasterMindSolver: IntMasterMindSolver
) : IMasterMindSolver<T> {
    constructor(codeLength: Int, codeItems: List<T>) : this(codeItems, IntMasterMindSolver(codeLength, codeItems.size))

    override val codeLength get() = intMasterMindSolver.codeLength
    override val unusedCodes get() =
        intMasterMindSolver.unusedCodes.map { code ->
            Code(code.map { codeItems[it] } )
        }
    override val s get() =
        intMasterMindSolver.s.map { code ->
            Code(code.map { codeItems[it] } )
        }
    val guessIdx get() = intMasterMindSolver.guess
    override val guess get() =
        Code(intMasterMindSolver.guess.map { codeItems[it] })
    override val allResponses get() = intMasterMindSolver.allResponses

    override fun allCodes() =
        intMasterMindSolver.allCodes().map { code ->
            Code(code.map { codeItems[it] } )
        }

    override fun processNext(response: Response) =
        intMasterMindSolver.processNext(response)
}

