package aoc2022

import java.io.File
import java.util.Stack

private val test = """
        [D]    
    [N] [C]    
    [Z] [M] [P]
     1   2   3 
    
    move 1 from 2 to 1
    move 3 from 1 to 3
    move 2 from 2 to 1
    move 1 from 1 to 2
""".trimIndent()

typealias Stacks = Map<Int, Stack<Char>>
typealias InstructionCallback = (stacks: Stacks, num: Int, from: Int, to: Int) -> Unit

private fun parse(startingStacks: String): Stacks {
    val colNumToIndex = Regex("""\d""").findAll(startingStacks.split("\n").last())
        .mapIndexed { i, match -> i + 1 to match.range.first }.toMap()
    val stacks = colNumToIndex.keys.associateWith { Stack<Char>() }

    startingStacks.split("\n").dropLast(1).reversed().forEach { row ->
        colNumToIndex.forEach { (i, col) ->
            val crate = row[col]
            if (crate.isLetter()) {
                stacks[i]?.add(crate)
            }
        }
    }

    return stacks
}

private fun compute(inp: String, callback: InstructionCallback): String {
    val instructionPattern = Regex("""move (\d+) from (\d+) to (\d+)""")
    val (startingStacks, rearrangement) = inp.split("\n\n")
    val stacks = parse(startingStacks)

    instructionPattern.findAll(rearrangement).forEach { instruction ->
        val (num, from, to) = instruction.groupValues.drop(1).map { it.toInt() }
        callback(stacks, num, from, to)
    }

    return stacks.values.map { it.last() }.joinToString("")
}

private fun partOne(inp: String): String {
    return compute(inp) { stacks, num, from, to ->
        repeat(num) { stacks[to]?.add(stacks[from]?.pop()) }
    }
}

private fun partTwo(inp: String): String {
    return compute(inp) { stacks, num, from, to ->
        val toMove = mutableListOf<Char>()
        repeat(num) { toMove.add(stacks[from]!!.pop()) }
        stacks[to]?.addAll(toMove.reversed())
    }
}

fun main() {
    val inp = File("app/src/main/resources/day05.txt").readText()
    println(partOne(inp))
    println(partTwo(inp))
}
