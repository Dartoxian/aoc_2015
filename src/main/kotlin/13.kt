data class HappinessEdge(val subject: String, val happinessChange: Int, val catalyst: String);

val pattern = "(\\w+) would (gain|lose) (\\d+) happiness units by sitting next to (\\w+).".toRegex()
fun String.toHappinessEdge(): HappinessEdge {
    val match = pattern.find(this) ?: throw IllegalArgumentException("Doesn't match pattern '$this'")
    return HappinessEdge(
        match.groupValues[1], when (match.groupValues[2]) {
            "gain" -> match.groupValues[3].toInt()
            "lose" -> -match.groupValues[3].toInt()
            else -> throw IllegalArgumentException("somehow else")
        }, match.groupValues[4]
    )
}

data class Graph(val edgeWeights: Map<Set<String>, Int>, val vertices: Set<String>) {
    fun totalWeight() = edgeWeights.values.sum()
    fun withEdge(connecting: Set<String>, weight: Int) = Graph(edgeWeights + (connecting to weight), vertices)
    fun hasVertexWithDegreeOverTwo() = vertices.any { v -> edgeWeights.filter { it.key.contains(v) }.count() > 2 }
    fun allVerticeHaveDegreeTwo() = vertices.all { v -> edgeWeights.filter { it.key.contains(v) }.count() == 2 }
    fun verticesReachableFrom(v: String) =
        edgeWeights.filter { it.key.contains(v) }.flatMap { it.key }.toSet() - setOf(v)

    fun hasPathBetween(v1: String, v2: String): Boolean {
        val explored = mutableSetOf(v1)
        val reachable = this.verticesReachableFrom(v1).toMutableList()
        while (reachable.size > 0) {
            val t_v = reachable.removeFirst()
            if (t_v == v2) {
                return true
            }
            explored.add(t_v)
            reachable.addAll(this.verticesReachableFrom(t_v) - explored)
        }
        return false
    }

    fun allVerticesConnected() = vertices.all { v1 -> vertices.filter { it != v1 }.all { hasPathBetween(v1, it) } }
}

fun main() {
    val input = getPuzzleInput(13, 1)
    val edges = input.split("\n").map(String::toHappinessEdge)

    val bidirecitonalEdges = edges
        .groupBy { setOf(it.subject, it.catalyst) }
        .mapValues { it.value.sumOf { it.happinessChange } }

    val orderedEdges = bidirecitonalEdges.entries.sortedBy { it.value }.reversed().map { it.toPair() }
    val guests = bidirecitonalEdges.entries.flatMap { it.key }.toSet()
    var initGraph = Graph(mapOf(), guests)

    fun findBestArrangment(extraEdges: List<Pair<Set<String>, Int>>, graph: Graph): Graph? {
        if (graph.hasVertexWithDegreeOverTwo()) {
            return null
        }
        if (graph.allVerticesConnected() && graph.allVerticeHaveDegreeTwo()) {
            return graph
        }
        if (extraEdges.isEmpty()) {
            return null
        }
        val withoutEdge = findBestArrangment(extraEdges.slice(1 until extraEdges.size), graph)
        val withEdge = findBestArrangment(
            extraEdges.slice(1 until extraEdges.size),
            graph.withEdge(extraEdges[0].first, extraEdges[0].second)
        )
        if (withoutEdge == null) return withEdge
        if (withEdge == null) return withoutEdge
        if (withEdge.totalWeight() > withoutEdge.totalWeight()) return withEdge else return withoutEdge
    }

    val bestGraph = findBestArrangment(orderedEdges, initGraph)!!
    println(bestGraph)

    println("the total happiness is ${bestGraph.totalWeight()}")

    val bidirecitonalEdgesWithMe = (bidirecitonalEdges + guests.map { setOf("me", it) to 0 }).toList()
    initGraph = Graph(mapOf(), guests + listOf("me"))
    val bestGraphWithMe = findBestArrangment(bidirecitonalEdgesWithMe, initGraph)!!
    println(bestGraphWithMe)
    println("The total happiness with me is ${bestGraphWithMe.totalWeight()}")

}