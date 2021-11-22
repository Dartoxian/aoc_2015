import java.math.BigInteger
import java.security.MessageDigest

fun main() {
    val p4_input = getPuzzleInput(4, 1)
    var hash = "";
    var i = 0;
    while (!hash.startsWith("00000")) {
        i += 1;
        hash = BigInteger(1, MessageDigest.getInstance("md5").digest((p4_input + i).toByteArray())).toString(16)
            .padStart(32, '0')
    }
    println("Santa can find an AdventCoin at $i")
    while (!hash.startsWith("000000")) {
        i += 1;
        hash = BigInteger(1, MessageDigest.getInstance("md5").digest((p4_input + i).toByteArray())).toString(16)
            .padStart(32, '0')
    }
    println("Santa can find an AdventCoin at $i")
}