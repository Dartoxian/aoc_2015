val directions = "^<v>"
val dx = listOf(0, 1, 0, -1)
val dy = listOf(1, 0, -1, 0)

fun main() {
    val p3_input = getPuzzleInput(3, 1);
    var x = 0;
    var y = 0
    var deliveries = mutableMapOf(((x to y) to 1)).withDefault { 0 }
    for (c in p3_input) {
        val d = directions.indexOf(c)
        if (d < 0) {
            throw IllegalArgumentException("Cannot parse $c")
        }
        x += dx[d]
        y += dy[d]
        deliveries[(x to y)] = deliveries.getValue(x to y) + 1
    }
    println("Santa delivered to ${deliveries.size} houses!")

    val position = mutableListOf(0 to 0, 0 to 0)
    var activePerson = 0;
    deliveries = mutableMapOf(((0 to 0) to 1)).withDefault { 0 }
    for (c in p3_input) {
        val d = directions.indexOf(c)
        if (d < 0) {
            throw IllegalArgumentException("Cannot parse $c")
        }
        position[activePerson] = position[activePerson].first + dx[d] to position[activePerson].second + dy[d]
        deliveries[position[activePerson]] = deliveries.getValue(position[activePerson]) + 1
        activePerson = (activePerson + 1) % position.size
    }
    println("Santa and Robo sanda delivered to ${deliveries.size} houses!")
}