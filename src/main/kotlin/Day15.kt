import java.io.File
import kotlin.math.abs
import kotlin.math.max

private val test = """
    Sensor at x=2, y=18: closest beacon is at x=-2, y=15
    Sensor at x=9, y=16: closest beacon is at x=10, y=16
    Sensor at x=13, y=2: closest beacon is at x=15, y=3
    Sensor at x=12, y=14: closest beacon is at x=10, y=16
    Sensor at x=10, y=20: closest beacon is at x=10, y=16
    Sensor at x=14, y=17: closest beacon is at x=10, y=16
    Sensor at x=8, y=7: closest beacon is at x=2, y=10
    Sensor at x=2, y=0: closest beacon is at x=2, y=10
    Sensor at x=0, y=11: closest beacon is at x=2, y=10
    Sensor at x=20, y=14: closest beacon is at x=25, y=17
    Sensor at x=17, y=20: closest beacon is at x=21, y=22
    Sensor at x=16, y=7: closest beacon is at x=15, y=3
    Sensor at x=14, y=3: closest beacon is at x=15, y=3
    Sensor at x=20, y=1: closest beacon is at x=15, y=3
""".trimIndent()

private data class Sensor(val x: Int, val y: Int, val beacon: Position) {

    fun asPos(): Position {
        return Pair(y, x)
    }

    fun distanceToBeacon(): Int {
        return distance(asPos(), beacon)
    }
}

private fun distance(src: Position, dst: Position): Int {
    return abs(src.first - dst.first) + abs(src.second - dst.second)
}

private fun parse(inp: String): Pair<List<Position>, List<Sensor>> {
    val sensorPattern = Regex("""Sensor at x=(\d+), y=(\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""")
    val beacons = mutableListOf<Position>()
    val sensors = sensorPattern.findAll(inp).map { sensor ->
        val args = sensor.groupValues.drop(1).map { it.toInt() }
        val beacon = Pair(args[3], args[2])
        beacons.add(beacon)
        Sensor(args[0], args[1], beacon)
    }.toList()

    return Pair(beacons, sensors)
}

private fun partOne(inp: String, targetRow: Int): Int {
    val (beacons, sensors) = parse(inp)
    val leftMost = sensors.minOf { sensor -> sensor.x - sensor.distanceToBeacon() }
    val rightMost = sensors.maxOf { sensor -> sensor.x + sensor.distanceToBeacon() }

    var impossibles = 0
    for (j in leftMost..rightMost) {
        val pos = Pair(targetRow, j)
        if (sensors.any { sensor ->
                distance(sensor.asPos(), pos) <= sensor.distanceToBeacon()
        } && !beacons.contains(pos)) {
            impossibles++
        }
    }

    return impossibles
}

private fun partTwo(inp: String, maxCoord: Int): Int {
    val (beacons, sensors) = parse(inp)

    return 0
}

fun main() {
    val inp = File("src/main/resources/day15.txt").readText().trim()
    println(partOne(inp, targetRow = 2000000))
    println(partTwo(inp, maxCoord = 4000000))
}
