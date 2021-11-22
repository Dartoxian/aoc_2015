import kotlin.math.max

val operations = mapOf(
    ("turn on" to { x: Boolean -> true }),
    ("toggle" to { x: Boolean -> !x }),
    ("turn off" to { x: Boolean -> false }),
)

val operationsP2 = mapOf(
    ("turn on" to { x: Int -> x + 1 }),
    ("toggle" to { x: Int -> x + 2 }),
    ("turn off" to { x: Int -> max(0, x - 1) }),
)

fun p1(p6_input: String) {
    val grid = (0..999).map { (0..999).map { false }.toMutableList() }
    val inputPattern = "(turn on|toggle|turn off) (\\d+),(\\d+) through (\\d+),(\\d+)".toRegex();
    for (l in p6_input.split("\n")) {
        val matches = inputPattern.find(l) ?: throw IllegalArgumentException("Cannot parse '$l'")
        val op = operations[matches.groupValues[1]]!!
        for (x in matches.groupValues[2].toInt()..matches.groupValues[4].toInt()) {
            for (y in matches.groupValues[3].toInt()..matches.groupValues[5].toInt()) {
                grid[x][y] = op(grid[x][y])
            }
        }
    }
    val totalOn = grid.map { it.map { if (it) 1 else 0}.sum() }.sum()
    println("There are $totalOn lights on.")
}

fun p2(p6_input:String) {
    val grid = (0..999).map { (0..999).map { 0 }.toMutableList() }
    val inputPattern = "(turn on|toggle|turn off) (\\d+),(\\d+) through (\\d+),(\\d+)".toRegex();
    for (l in p6_input.split("\n")) {
        val matches = inputPattern.find(l) ?: throw IllegalArgumentException("Cannot parse '$l'")
        val op = operationsP2[matches.groupValues[1]]!!
        for (x in matches.groupValues[2].toInt()..matches.groupValues[4].toInt()) {
            for (y in matches.groupValues[3].toInt()..matches.groupValues[5].toInt()) {
                grid[x][y] = op(grid[x][y])
            }
        }
    }
    val totalOn = grid.map { it.sum() }.sum()
    println("There is $totalOn total brightness.")
}

fun main() {
    val p6_input = getPuzzleInput(6, 1)

    p1(p6_input)
    p2(p6_input)
}