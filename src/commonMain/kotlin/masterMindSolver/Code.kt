package masterMindSolver


class Code<T>(val values: List<T>): List<T> by values {
    constructor(size: Int, init: (Int) -> T) : this(List(size, init))

    override fun toString() = values.toString()
}

fun Code<Int>.match(other: Code<Int>): Response {
    var black = 0
    var white = 0
    val a = toIntArray()
    val b = other.toIntArray()
    for (i in a.indices) {
        if (a[i] != -1 && a[i] == b[i]) {
            black++
            a[i] = -1
            b[i] = -1
        }
    }
    for (iA in a.indices) {
        if (a[iA] == -1) continue
        for (iB in b.indices) {
            if (a[iA] == b[iB]) {
                white++
                b[iB] = -1
                break
            }
        }
    }
    return Response(black, white)
}

fun Code<Int>.matchResponse(other: Code<Int>, response: Response): Boolean {
    var black = 0
    var white = 0
    val a = toIntArray()
    val b = other.toIntArray()
    for (i in a.indices) {
        if (a[i] != -1 && a[i] == b[i]) {
            black++
            //if (black + size - i - 1 < response.black) return false
            a[i] = -1
            b[i] = -1
        }
    }
    if (black != response.black) return false
    for (iA in a.indices) {
        //if (white + size - iA < response.white) return false
        if (a[iA] == -1) continue
        for (iB in b.indices) {
            if (a[iA] == b[iB]) {
                white++
                //if (white > response.white) return false
                b[iB] = -1
                break
            }
        }
    }
    return white == response.white
}