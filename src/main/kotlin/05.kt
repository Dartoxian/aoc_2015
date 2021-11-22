fun containsAtLeast3Vowels(it: String): Boolean = it.replace("[^aeiou]".toRegex(), "").length >= 3
fun containsDoubleLetter(it: String): Boolean = it.slice(1 until it.length).filterIndexed { i, c -> c == it[i] }.any()
fun containsForbiddenSubstring(it: String): Boolean = "ab" in it || "cd" in it || "pq" in it || "xy" in it

fun String.isNice(): Boolean =
    containsAtLeast3Vowels(this) && containsDoubleLetter(this) && !containsForbiddenSubstring(this);

fun containsRepeatingPairOfCharacters(it: String): Boolean {
    val substringPlaces = mutableMapOf<String, MutableList<Int>>()
    for (i in 1 until it.length) {
        substringPlaces.putIfAbsent(it.slice(i - 1..i), mutableListOf())
        substringPlaces.get(it.slice(i - 1..i))?.add(i - 1)
    }
    return substringPlaces.values
        .filter { it.size > 1}
        .filter { it.fold(listOf<Int>()) { acc, i ->
            if (acc.size == 0) {
                listOf(i)
            } else {
                if (acc.last() == i - 1) {
                    acc
                } else {
                    acc + listOf(i)
                }
            }
        }.size > 1 }
        .any()
}

fun containsDoubleLetterWithGap(it: String): Boolean =
    it.slice(2 until it.length).filterIndexed { i, c -> c == it[i] }.any()

fun String.isRevisedNice(): Boolean =
    containsDoubleLetterWithGap(this) && containsRepeatingPairOfCharacters(this)

fun main() {
    val p5_input = getPuzzleInput(5, 1)
    var niceStrings = p5_input.split("\n").filter(String::isNice).count()
    println("There are $niceStrings nice strings")

    var newNiceStrings = p5_input.split("\n").filter(String::isRevisedNice).count()
    println("There are $newNiceStrings new nice strings")
}