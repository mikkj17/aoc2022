package aoc2022

import java.io.File

private val test = """
    30373
    25512
    65332
    33549
    35390
""".trimIndent()

typealias Matrix = List<List<Int>>

private fun getMatrixHeightWidth(inp: String): Pair<Matrix, Pair<Int, Int>> {
    val matrix = inp.split("\n").map { row -> row.map { it.code } }
    return Pair(
        matrix,
        Pair(matrix.size, matrix[0].size),
    )
}

private fun rowCol(matrix: Matrix, i: Int, j: Int): Pair<List<Int>, List<Int>> {
    return Pair(
        matrix[i],
        matrix.map { it[j] },
    )
}

private fun isVisible(matrix: Matrix, i: Int, j: Int): Boolean {
    val (row, col) = rowCol(matrix, i, j)
    val directions = listOf(
        row.slice(0 until j),
        row.slice(j + 1 until row.size),
        col.slice(0 until i),
        col.slice(i + 1 until col.size),
    )
    return directions.any { !it.any { tree -> tree >= matrix[i][j] } }
}

private fun partOne(inp: String): Int {
    val (matrix, dimensions) = getMatrixHeightWidth(inp)
    val (height, width) = dimensions
    val edges = 2 * (height - 1) + 2 * (height - 1)

    return edges + (1 until height - 1).flatMap {
        i -> (1 until width - 1).map { j -> isVisible(matrix, i , j) }
    }.filter { it }.size
}

private fun singleViewingDistance(trees: List<Int>, value: Int): Int {
    var distance = 0
    for (tree in trees) {
        distance++
        if (tree >= value) {
            break
        }
    }

    return distance
}

private fun viewingDistance(matrix: Matrix, i: Int, j: Int): List<Int> {
    val (row, col) = rowCol(matrix, i, j)

    val directions = listOf(
        row.slice(0 until j).reversed(),
        row.slice(j + 1 until row.size),
        col.slice(0 until i).reversed(),
        col.slice(i + 1 until col.size),
    )

    return directions.map { singleViewingDistance(it, matrix[i][j]) }
}

private fun partTwo(inp: String): Int {
    val (matrix, dimensions) = getMatrixHeightWidth(inp)
    val (height, width) = dimensions
    val distances = (1 until height - 1).flatMap { i ->
        (1 until width - 1).map { j -> viewingDistance(matrix, i, j) }
    }

    return distances.maxOf { it.fold(1) { acc, value -> acc * value } }
}

fun main() {
    val inp = File("app/src/main/resources/day08.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
