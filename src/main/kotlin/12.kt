import com.google.gson.JsonElement
import com.google.gson.JsonParser

fun JsonElement.sumValues(ignoreRed: Boolean = false): Int {
    if (this.isJsonNull) {
        return 0
    }
    if (this.isJsonPrimitive) {
        return if (this.asJsonPrimitive.isNumber) this.asInt else 0
    }
    if (this.isJsonArray) {
        return this.asJsonArray.sumOf { it.sumValues(ignoreRed) }
    }
    if (this.isJsonObject) {
        if (ignoreRed && this.asJsonObject.entrySet()
                .any { it.value.isJsonPrimitive && it.value.asJsonPrimitive.asString == "red" }
        ) {
            return 0
        }
        return this.asJsonObject.entrySet().sumOf { it.value.sumValues(ignoreRed) }
    }
    throw IllegalArgumentException("Cannot sum $this")
}

fun main() {
    val p12_input = getPuzzleInput(12, 1);
    val parsed = JsonParser.parseString(p12_input).asJsonObject
    println("The sum of values are ${parsed.sumValues()}")
    println("The sum of values excluding red are ${parsed.sumValues(true)}")
}