package aoc2022

import java.io.File

private val test = """
    noop
    addx 3
    addx -5
""".trimIndent()

private val test2 = """
    addx 15
    addx -11
    addx 6
    addx -3
    addx 5
    addx -1
    addx -8
    addx 13
    addx 4
    noop
    addx -1
    addx 5
    addx -1
    addx 5
    addx -1
    addx 5
    addx -1
    addx 5
    addx -1
    addx -35
    addx 1
    addx 24
    addx -19
    addx 1
    addx 16
    addx -11
    noop
    noop
    addx 21
    addx -15
    noop
    noop
    addx -3
    addx 9
    addx 1
    addx -3
    addx 8
    addx 1
    addx 5
    noop
    noop
    noop
    noop
    noop
    addx -36
    noop
    addx 1
    addx 7
    noop
    noop
    noop
    addx 2
    addx 6
    noop
    noop
    noop
    noop
    noop
    addx 1
    noop
    noop
    addx 7
    addx 1
    noop
    addx -13
    addx 13
    addx 7
    noop
    addx 1
    addx -33
    noop
    noop
    noop
    addx 2
    noop
    noop
    noop
    addx 8
    noop
    addx -1
    addx 2
    addx 1
    noop
    addx 17
    addx -9
    addx 1
    addx 1
    addx -3
    addx 11
    noop
    noop
    addx 1
    noop
    addx 1
    noop
    noop
    addx -13
    addx -19
    addx 1
    addx 3
    addx 26
    addx -30
    addx 12
    addx -1
    addx 3
    addx 1
    noop
    noop
    noop
    addx -9
    addx 18
    addx 1
    addx 2
    noop
    noop
    addx 9
    noop
    noop
    noop
    addx -1
    addx 2
    addx -37
    addx 1
    addx 3
    noop
    addx 15
    addx -21
    addx 22
    addx -6
    addx 1
    noop
    addx 2
    addx 1
    noop
    addx -10
    noop
    noop
    addx 20
    addx 1
    addx 2
    addx 2
    addx -6
    addx -11
    noop
    noop
    noop
""".trimIndent()

private fun partOne(inp: String): Int {
    val cycles = listOf(20, 60, 100, 140, 180, 220)
    var cycle = 1
    var registerX = 1
    val signalStrengths = mutableListOf<Pair<Int, Int>>()
    for (instruction in inp.split("\n")) {
        if (++cycle in cycles) {
            signalStrengths.add(Pair(cycle, registerX))
        }
        if (instruction == "noop") {
            continue
        }
        else {

            val value = instruction.split(" ").last().toInt()
            registerX += value
            if (++cycle in cycles) {
                signalStrengths.add(Pair(cycle, registerX))
            }
        }
    }

    return signalStrengths.sumOf { it.first * it.second }
}

private fun partTwo(inp: String): String {
    var registerX = 1
    val rows = mutableListOf<String>()
    var row = ""

    inp.split("\n").forEach {
        row += if (row.length in (registerX - 1..registerX + 1)) "#" else "."
        if (row.length == 40) {
            rows.add(row)
            row = ""
        }
        if (it != "noop") {
            row += if (row.length in (registerX - 1..registerX + 1)) "#" else "."
            if (row.length == 40) {
                rows.add(row)
                row = ""
            }
            registerX += it.split(" ").last().toInt()
        }
    }

    return rows.joinToString("\n") { it.fold("") { acc, c -> "$acc $c" } }
}

fun main() {
    val inp = File("app/src/main/resources/day10.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
