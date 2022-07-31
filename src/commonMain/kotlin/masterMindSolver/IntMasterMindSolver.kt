package masterMindSolver

/*
src: https://en.m.wikipedia.org/wiki/Mastermind_(board_game)

Before asking for a best strategy of the codebreaker one has to define what is the meaning of "best": The minimal
number of moves can be analyzed under the conditions of worst and average case and in the sense of a minimax value of
a zero-sum game in game theory.
Best strategies with four pegs and six colorsEdit

With four pegs and six colours, there are 6^4 = 1,296 different patterns (allowing duplicate colours).
Worst case: Five-guess algorithmEdit

In 1977, Donald Knuth demonstrated that the codebreaker can solve the pattern in five moves or fewer, using an
algorithm that progressively reduces the number of possible patterns.[12] The algorithm works as follows:

 1. Create the set S of 1,296 possible codes {1111, 1112, ... 6665, 6666}.
 2. Start with initial guess 1122. (Knuth gives examples showing that this algorithm using first guesses other than
    "two pair"; such as 1111, 1112, 1123, or 1234; does not win in five tries on every code.)
 3. Play the guess to get a response of coloured and white pegs.
 4. If the response is four colored pegs, the game is won, the algorithm terminates.
 5. Otherwise, remove from S any code that would not give the same response.
 6. The next guess is chosen by the minimax technique, which chooses a guess that has the least worst response
    score. In this case, a response to a guess is some number of coloured and white pegs, and the score of such a
    response is defined to be the number of codes in S that are still possible even after the response is known.
    The score of a guess is pessimistically defined to be the worst (maximum) of all its response scores. From the
    set of guesses with the least (minimum) guess score, select one as the next guess, choosing a code from S
    whenever possible. (Within these constraints, Knuth follows the convention of choosing the guess with the least
    numeric value; e.g., 2345 is lower than 3456. Knuth also gives an example showing that in some cases no code
    from S will be among the best scoring guesses and thus the guess cannot win on the next turn, yet will be
    necessary to assure a win in five.)
 7. Repeat from step 3.

Average caseEdit

Subsequent mathematicians have been finding various algorithms that reduce the average number of turns needed to
solve the pattern: in 1993, Kenji Koyama and Tony W. Lai performed an exhaustive depth-first search showing that
the optimal method for solving a random code could achieve an average of 5,625/1,296 = 4.3403 turns to solve, with
a worst-case scenario of six turns.[13]
Minimax value of game theoryEdit

The minimax value in the sense of game theory is 5,600/1,290 = 4.341. The minimax strategy of the codemaker consists
in a uniformly distributed selection of one of the 1,290 patterns with two or more colors.[14]
*/


class IntMasterMindSolver(
    override val codeLength: Int = 4,
    val base: Int = 6,
) : IMasterMindSolver<Int> {
    override var unusedCodes = allCodes(); private set
    override var s = unusedCodes; private set
    override var guess = firstGuess(); private set

    override val allResponses = allResponses().toList()

    override fun processNext(response: Response) {
        if (response.isSolved()) return

        if (response !in allResponses) throw IllegalStateException("Illegal Response!")
        s = filterCodes(s, guess, response)
        if (s.isEmpty()) throw IllegalStateException("At least one Response was wrong!")
        var minRate = Int.MAX_VALUE
        var fromS = false
        for (guess1 in unusedCodes) {
            var maxRate = 0
            for (response1 in allResponses) {
                maxRate = maxOf(maxRate, rateCode(s, guess1, response1))
            }
            if (maxRate < minRate) {
                minRate = maxRate
                guess = guess1
                fromS = guess1 in s
            } else if (maxRate == minRate && !fromS && guess1 in s) {
                guess = guess1
                fromS = true
            }
        }
        unusedCodes = unusedCodes.filter { it != (guess) }
    }

    override fun allCodes(): List<Code<Int>> {
        var size = base; repeat(codeLength - 1) { size *= base }
        return List(size) { i ->
            Code(this.codeLength) { pos ->
                var n = i; repeat(codeLength - pos - 1) { n /= base }
                n % base
            }
        }
    }

    private fun allResponses() = sequence {
        for (black in 0..codeLength)
            for (white in 0..codeLength - black)
                yield(Response(black, white))
    }

    private fun firstGuess(): Code<Int> {
        val list1 = List(codeLength / 2) { 0 }
        val list2 = List(codeLength - list1.size) { 1 }
        val guess = Code(list1 + list2)
        unusedCodes = unusedCodes.filter { it != (guess) }
        return guess
    }

    private fun filterCodes(
        codes: List<Code<Int>>,
        guess: Code<Int>,
        response: Response
    ) = codes.filter { it.match(guess) == response }

    private fun rateCode(
        codes: List<Code<Int>>,
        guess: Code<Int>,
        response: Response
    ) = codes.count { it.matchResponse(guess, response) }
}