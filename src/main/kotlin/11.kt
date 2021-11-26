fun incrementingPassword(oldPassword: String): Sequence<String> = sequence {
    var p = oldPassword.reversed()
    while (true) {
        val nextPassword = StringBuilder()
        var incNext = true
        for (c in p) {
            if (incNext) {
                if (c == 'z') {
                    nextPassword.append('a')
                } else {
                    incNext = false
                    nextPassword.append((c.code + 1).toChar())
                }
            } else {
                nextPassword.append(c)
            }
        }
        p = nextPassword.toString()
        yield(p.reversed())
    }
}

fun String.containsRunOfLetters(): Boolean {
    for (i in 0 until this.length - 2) {
        if (this[i].code == this[i + 1].code - 1 && this[i + 1].code == this[i + 2].code - 1) {
            return true
        }
    }
    return false
}

fun String.containsConfusingLetters() = 'i' in this || 'o' in this || 'l' in this

fun String.containsTwoDoubleLetters(): Boolean {
    val doubleLetterPattern = "(.)\\1".toRegex()
    val matches = doubleLetterPattern.findAll(this)
    return matches.map { it.groupValues[0] }.distinct().count() >= 2
}

fun String.isValidPassword() = this.containsRunOfLetters() && !this.containsConfusingLetters() && this.containsTwoDoubleLetters()

fun main() {
    val p11_input = getPuzzleInput(11, 1)
    var validPasswords = incrementingPassword(p11_input).filter { it.isValidPassword() }
    var candidatePasswords = validPasswords.take(2).toList()
    println("Santas next valid password is ${candidatePasswords[0]}")
    println("Santas next valid password is ${candidatePasswords[1]}")
}