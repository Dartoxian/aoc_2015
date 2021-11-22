fun main() {
    val p1_input = getPuzzleInput(1,1);
    var floor = 0;
    for (i in 0 until p1_input.length) {
        floor += when (p1_input[i]) {
            '(' ->  1
            ')' -> -1
            else -> throw IllegalArgumentException("Blah")
        }
        if (floor == -1) {
            println("Santa entered the basement on position ${i + 1}")
        }
    }
    println("Santa is on floor $floor")

}