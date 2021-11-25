fun String.lookAndSayGame(): String {
    val r = StringBuilder()
    var i = 0
    while (i < this.length) {
        val c = this[i]
        i++
        while (i < this.length - 1 && c == this[i + 1]) {
            i++
        }

        r.append(i.toString())
        r.append(c)
    }
    return r.toString()
}

fun main() {
    val p10_input = getPuzzleInput(10, 1)
    var gameResult = p10_input
    (1..1).forEach { gameResult = gameResult.lookAndSayGame() }
    println("Output length after 40 games ${gameResult.length}")
}