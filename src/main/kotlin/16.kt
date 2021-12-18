data class Sue(val id: Int, val posessions: Map<String, Int>) {
    fun maybeSameAs(other: Sue) = posessions.all { it.value == other.posessions.get(it.key) }
    fun maybeSameAsFixedRetroencabulator(other: Sue) = posessions.all {
        (it.key !in setOf("cats", "trees") || it.value > other.posessions.getOrDefault(it.key,0)) &&
        (it.key !in setOf("pomeranians", "goldfish") || it.value < other.posessions.getOrDefault(it.key, 0)) &&
        (it.key in setOf("cats", "trees", "pomeranians", "goldfish") || it.value == other.posessions.get(it.key))
    }
}

val suePattern = "Sue (\\d+): (.*)".toRegex()
fun String.toSue(): Sue {
    val matches = suePattern.find(this) ?: throw IllegalArgumentException("'$this' is not a Sue")
    val posessions = matches.groupValues[2]
        .split(", ")
        .map { it.split(": ") }
        .map { it[0] to it[1].toInt() }.toMap()
    return Sue(matches.groupValues[1].toInt(), posessions)
}

fun main() {
    val sues = getPuzzleInput(16, 1).split("\n").map(String::toSue)
    val candidateSue = Sue(0, mapOf(
        "children" to 3,
            "cats" to 7,
            "samoyeds" to 2,
            "pomeranians" to 3,
            "akitas" to 0,
            "vizslas" to 0,
            "goldfish" to 5,
            "trees" to 3,
            "cars" to 2,
            "perfumes" to 1,
    ))
    println(sues.filter { it.maybeSameAs(candidateSue) })
    println(sues.filter { it.maybeSameAsFixedRetroencabulator(candidateSue) })
}