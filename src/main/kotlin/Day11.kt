import java.io.File

private val test = """
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1
""".trimIndent()

private data class Monkey(
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val mod: Int,
    val ifTrue: Int,
    val ifFalse: Int,
)

private fun getOperation(operation: String): (Long) -> Long {
    fun add(a: Long, b: Long) = a + b
    fun multiply(a: Long, b: Long) = a * b
    fun square(a: Long): Long = a * a

    if (operation.endsWith("old")) {
        return ::square
    }
    val n = operation.split(" ").last().toLong()
    val func = if (operation.startsWith("+")) ::add else ::multiply
    return { func(it, n) }
}

private fun parse(inp: String): List<Monkey> {
    return inp.split("\n\n").map { monkeyInfo ->
        val rows = monkeyInfo.split("\n")
        val operationString = rows[2].split(" = old ").last()
        Monkey(
            items = rows[1].split(": ").last().split(", ").map { it.toLong() }.toMutableList(),
            operation = getOperation(operationString),
            mod = rows[3].split(" ").last().toInt(),
            ifTrue = rows[4].split(" ").last().toInt(),
            ifFalse = rows[5].split(" ").last().toInt(),
        )
    }
}

private fun partOne(inp: String): Int {
    val monkeys = parse(inp)
    val activeness = (monkeys.indices).map { 0 }.toMutableList()

    repeat(20) {
        monkeys.forEachIndexed { i, monkey ->
            monkey.items.forEach { item ->
                activeness[i]++
                val new = monkey.operation(item) / 3
                monkeys[if (new % monkey.mod == 0.toLong()) monkey.ifTrue else monkey.ifFalse].items.add(new)
            }
            monkey.items.clear()
        }
    }

    return activeness.sorted().takeLast(2).fold(1) {acc, i -> acc * i }
}

private fun partTwo(inp: String): Long {
    val monkeys = parse(inp)
    val activeness = (monkeys.indices).map { 0 }.toMutableList()
    val modulos = monkeys.map { it.mod }.fold(1) { acc, i -> acc * i }

    repeat(10000) {
        monkeys.forEachIndexed { i, monkey ->
            monkey.items.forEach { item ->
                activeness[i]++
                val new = monkey.operation(item) % modulos
                monkeys[if (new % monkey.mod == 0.toLong()) monkey.ifTrue else monkey.ifFalse].items.add(new)
            }
            monkey.items.clear()
        }
    }

    return activeness.sorted().takeLast(2).fold(1) {acc, i -> acc * i }
}

fun main() {
    val inp = File("src/main/resources/day11.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
