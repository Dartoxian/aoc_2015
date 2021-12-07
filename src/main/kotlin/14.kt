import kotlin.math.floor
import kotlin.math.min

data class Reindeer(val name: String, val speed: Int, val flightDuration: Int, val restDuration: Int) {
    val loopDuration = flightDuration +  restDuration
    fun distanceAfter(seconds: Int) =
        ((flightDuration * floor(seconds.toDouble() / loopDuration)) + min(seconds % loopDuration, flightDuration)) * speed
    fun plotJourneyUntil(seconds: Int) = sequence {
        var p = 0
        for (i in 0..seconds) {
            yield(p)
            if (i % loopDuration < flightDuration) {
                p += speed
            }
        }
    }.toList()
}

val reindeerPattern = "(\\w+) can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds.".toRegex()
fun String.toReindeer() = with(reindeerPattern.find(this) ?: throw IllegalArgumentException("Cannot parse $this")) {
    Reindeer(this.groupValues[1], this.groupValues[2].toInt(), this.groupValues[3].toInt(), this.groupValues[4].toInt())
}

fun main() {
    val inputs = getPuzzleInput(14, 1)
    val reindeer = inputs.split("\n").map(String::toReindeer)
    val furthestTravelled = reindeer.map { it.distanceAfter(2503) }.maxOrNull()
    println("The furthest travelled is $furthestTravelled")

    val points = reindeer.map { it.name to 0 }.toMap().toMutableMap()
    val journies = reindeer.map { it.name to it.plotJourneyUntil(2503) }
    for (i in 1..2503) {
        val positions = journies.map { it.first to it.second[i] }
        val currentFurthest = positions.maxOf { it.second }
        positions.filter { it.second == currentFurthest }.forEach { points[it.first] = points[it.first]!! + 1 }
    }
    println("The winning reindeer has score ${points.maxOf { it.value }}")
}