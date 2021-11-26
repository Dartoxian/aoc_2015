fun String.lookAndSayGame(): String {
    val r = StringBuilder()
    var i = 0
    while (i < this.length) {
        val c = this[i]
        var t = 0
        while (i < this.length && c == this[i]) {
            i++
            t++
        }

        r.append(t.toString())
        r.append(c)
    }
    return r.toString()
}

fun main() {
    val p10_input = getPuzzleInput(10, 1)
    var gameResult = p10_input
    (1..40).forEach { gameResult = gameResult.lookAndSayGame() }
    println("Output length after 40 games ${gameResult.length}")
    (1..10).forEach { gameResult = gameResult.lookAndSayGame() }
    println("Output length after 50 games ${gameResult.length}")
}