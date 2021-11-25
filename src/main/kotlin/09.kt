fun main() {
    val p9_input = getPuzzleInput(9, 1)
    val pat = "(?<from>\\w+) to (?<to>\\w+) = (?<dist>\\d+)".toRegex()
    val edges = mutableMapOf<String, Set<String>>().withDefault { setOf<String>() }
    val edgeCost = mutableMapOf<Set<String>, Int>()
    for (l in p9_input.split("\n")) {
        val match = pat.find(l) ?: throw IllegalArgumentException("Could not parse $l")
        val v1 = match.groups["from"]!!.value
        val v2 = match.groups["to"]!!.value
        val d = match.groups["dist"]!!.value.toInt()
        edges[v1] = edges.getValue(v1) + setOf(v2)
        edges[v2] = edges.getValue(v2) + setOf(v1)
        edgeCost[setOf(v1, v2)] = d
    }

    fun lengthOfShortestExplorationFrom(n: String, visitedNodes: Set<String>): Int {
        if (visitedNodes.size == edges.keys.size) {
            return 0
        }
        var lowestCost = Int.MAX_VALUE
        for (v in (edges[n]!! - visitedNodes)) {
            val c = edgeCost[setOf(n, v)]!! + lengthOfShortestExplorationFrom(v, visitedNodes + setOf(v))
            if (c < lowestCost) {
                lowestCost = c
            }
        }
        return lowestCost
    }

    fun lengthOfLongestExplorationFrom(n: String, visitedNodes: Set<String>): Int {
        if (visitedNodes.size == edges.keys.size) {
            return 0
        }
        var highestCost = Int.MIN_VALUE
        for (v in (edges[n]!! - visitedNodes)) {
            val c = edgeCost[setOf(n, v)]!! + lengthOfLongestExplorationFrom(v, visitedNodes + setOf(v))
            if (c > highestCost) {
                highestCost = c
            }
        }
        return highestCost
    }

    val lowestPossibleCost = edges.keys.map { lengthOfShortestExplorationFrom(it, setOf(it)) }.minOrNull()
    val highestPossibleCost = edges.keys.map { lengthOfLongestExplorationFrom(it, setOf(it)) }.maxOrNull()
    println("The lowest possible cost is $lowestPossibleCost")
    println("The highest possible cost is $highestPossibleCost")
}