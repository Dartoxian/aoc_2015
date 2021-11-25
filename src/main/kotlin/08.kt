fun String.santaEscape(): String {
    val r = StringBuilder()
    r.append('"')
    for (c in this) {
        when (c) {
            '"' -> r.append("\\\"")
            '\\' -> r.append("\\\\")
            else -> r.append(c)
        }
    }
    r.append('"')
    return r.toString()
}

fun String.santaUnescape(): String {
    val r = StringBuilder()
    var i = 0;
    while (i < this.length) {
        if ((i == 0 || i == this.length - 1) && this[i] == '"') {
            i++
            continue
        }
        if (this[i] == '\\') {
            i++
            when (this[i]) {
                '"' -> r.append(this[i])
                '\\' -> r.append(this[i])
                'x' ->  {
                    r.append(Char(this.slice(i + 1..i + 2).toInt(16)))
                    i += 2
                }
            }
            i++
        } else {
            r.append(this[i])
            i++
        }
    }
    return r.toString()
}

fun main() {
    val p8_input = getPuzzleInput(8, 1)
    var totalChars = 0;
    var totalMemoryChars = 0;
    var totalExtraEscapeChars = 0;
    for (l in p8_input.split("\n")) {
        totalChars += l.length
        totalMemoryChars += l.santaUnescape().length
        totalExtraEscapeChars += l.santaEscape().length
    }
    println("The code-memory difference is list lengths is ${totalChars - totalMemoryChars}")
    println("The extra escape-code difference is list lengths is ${totalExtraEscapeChars - totalChars}")
}