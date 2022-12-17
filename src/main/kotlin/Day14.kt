import java.io.File

private val test = """
    498,4 -> 498,6 -> 496,6
    503,4 -> 502,4 -> 502,9 -> 494,9
""".trimIndent()

private fun getRockLine(src: List<Int>, dst: List<Int>): Set<Position> {
    val line = mutableSetOf<Position>()
    val horizontal = listOf(src.first(), dst.first()).sorted()
    val vertical = listOf(src.last(), dst.last()).sorted()

    for (x in horizontal.first()..horizontal.last()) {
        for (y in vertical.first()..vertical.last()) {
            line.add(Pair(x, y))
        }
    }

    return line
}

private fun parse(inp: String): MutableSet<Position> {
    val rocks = mutableSetOf<Position>()
    inp.split("\n").forEach { path ->
        path.split(" -> ").windowed(2).forEach { line ->
            val (src, dst) = line.map { coord -> coord.split(",").map { it.toInt() } }
            rocks.addAll(getRockLine(src, dst))
        }

    }

    return rocks
}

private fun simulate(rocks: MutableSet<Position>, breakCallback: (Position) -> Boolean): Int {
    var sandCounter = 0
    var breakOuter = false
    while (true) {
        var sandPos = Pair(500, 0)
        while (true) {
            if (breakCallback(sandPos)) {
                breakOuter = true
                break
            }

            var newPos = Pair(sandPos.first, sandPos.second + 1)
            if (!rocks.contains(newPos)) {
                sandPos = newPos
                continue
            }

            newPos = Pair(sandPos.first - 1, sandPos.second + 1)
            if (!rocks.contains(newPos)) {
                sandPos = newPos
                continue
            }

            newPos = Pair(sandPos.first + 1, sandPos.second + 1)
            if (!rocks.contains(newPos)) {
                sandPos = newPos
                continue
            }

            rocks.add(sandPos)
            break
        }

        if (breakOuter) {
            break
        }

        sandCounter++
    }

    return sandCounter
}

private fun partOne(inp: String): Int {
    val rocks = parse(inp)
    val depth = rocks.maxBy { it.second }.second
    return simulate(rocks) { sandPos -> sandPos.second == depth }
}

private fun partTwo(inp: String): Int {
    val rocks = parse(inp)
    val depth = rocks.maxBy { it.second }.second + 2

    for (x in 500 - (2 * depth)..500 + (2 * depth)) {
        rocks.add(Pair(x, depth))
    }

    return simulate(rocks) { _ -> Pair(500, 0) in rocks }
}

fun main() {
    val inp = File("src/main/resources/day14.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
