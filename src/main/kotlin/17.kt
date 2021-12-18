fun List<Int>.getValidContainersFor(q: Int): Sequence<List<Int>> = sequence {
    if (this@getValidContainersFor.isEmpty()) {
        if (q == 0) {
            yield(listOf())
        }
        return@sequence
    }
    if (this@getValidContainersFor[0] <= q) {
        yieldAll(
            this@getValidContainersFor.slice(1 until this@getValidContainersFor.size)
                .getValidContainersFor(q - this@getValidContainersFor[0])
                .map { it + this@getValidContainersFor[0] })
    }
    yieldAll(
        this@getValidContainersFor.slice(1 until this@getValidContainersFor.size)
            .getValidContainersFor(q)
            .map { it })
}

fun main() {
    val containers = getPuzzleInput(17, 1).split("\n").map(String::toInt)
    val validContainers = containers.sorted().reversed().getValidContainersFor(150).toList()
    val numberCombos = validContainers.count()
    val shortestCombo = validContainers.minOf { it.size }
    val numberShortestCombo = validContainers.count { it.size == shortestCombo }
    println(numberCombos)
    println(numberShortestCombo)
}