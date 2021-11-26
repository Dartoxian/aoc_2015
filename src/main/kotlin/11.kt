fun incrementingPassword(oldPassword: String): Sequence<String> = sequence {
    var p = oldPassword.reversed()
    while (true) {
        val nextPassword = StringBuilder()
        
        p = nextPassword.toString()
        yield(p.reversed())
    }
}