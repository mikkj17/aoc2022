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

private fun parse(startingStacks: String): MutableMap<Int, Stack<Char>> {
    val cols = Regex("""\d""").findAll(startingStacks.split("\n").last()).map { it.range.first }.toList()

    val stacks = mutableMapOf<Int, Stack<Char>>()
    for (i in cols.indices) {
        stacks[i + 1] = Stack()
    }

    for (row in startingStacks.split("\n").dropLast(1).reversed()) {
        for ((i, col) in cols.withIndex()) {
            val crate = row[col]
            if (crate == ' ') {
                continue
            }
            stacks[i+1]!!.add(crate)
        }
    }

    return stacks
}

private fun partOne(inp: String): String {
    val (startingStacks, rearrangement) = inp.split("\n\n")
    val stacks = parse(startingStacks)

    val instructionPattern = Regex("""move (\d+) from (\d+) to (\d+)""")
    instructionPattern.findAll(rearrangement).forEach { instruction ->
        val (num, from, to) = instruction.groupValues.drop(1).map { it.toInt() }
        for (i in 0 until num) {
            stacks[to]!!.add(stacks[from]!!.pop())
        }
    }

    return stacks.values.map { it.last() }.joinToString("")
}

private fun partTwo(inp: String): String {
    val (startingStacks, rearrangement) = inp.split("\n\n")
    val stacks = parse(startingStacks)

    val instructionPattern = Regex("""move (\d+) from (\d+) to (\d+)""")
    instructionPattern.findAll(rearrangement).forEach { instruction ->
        val (num, from, to) = instruction.groupValues.drop(1).map { it.toInt() }
        val toMove = mutableListOf<Char>()
        for (i in 0 until num) {
            toMove.add(stacks[from]!!.pop())
        }
        stacks[to]!!.addAll(toMove.reversed())
    }

    return stacks.values.map { it.last() }.joinToString("")
}

fun main() {
    val inp = File("src/main/resources/day05.txt").readText()
    println(partOne(inp))
    println(partTwo(inp))
}
