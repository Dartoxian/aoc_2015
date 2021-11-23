abstract class Gate {
    var resolvedValue: Int? = null
    fun resolve(gateLookup: Map<String, Gate>): Int {
        if (resolvedValue == null) {
            resolvedValue = this.doResolve(gateLookup)
        }
        return resolvedValue!!
    }

    protected abstract fun doResolve(gateLookup: Map<String, Gate>): Int;
}

data class AndGate(val left: String, val right: String): Gate() {
    override fun doResolve(gateLookup: Map<String, Gate>): Int {
        val l = gateLookup[left] ?: RawValueGate(left.toInt())
        val r = gateLookup[right] ?: RawValueGate(right.toInt())
        return l.resolve(gateLookup).and(r.resolve(gateLookup))
    }
}

data class OrGate(val left: String, val right: String): Gate() {
    override fun doResolve(gateLookup: Map<String, Gate>): Int {
        val l = gateLookup[left] ?: RawValueGate(left.toInt())
        val r = gateLookup[right] ?: RawValueGate(right.toInt())
        return l.resolve(gateLookup).or(r.resolve(gateLookup))
    }
}

data class NotGate(val input: String): Gate() {
    override fun doResolve(gateLookup: Map<String, Gate>): Int {
        return gateLookup[input]!!.resolve(gateLookup).inv()
    }
}

data class LShiftGate(val input: String, val shiftAmount: Int): Gate() {
    override fun doResolve(gateLookup: Map<String, Gate>): Int {
        return gateLookup[input]!!.resolve(gateLookup).shl(shiftAmount)
    }
}

data class RShiftGate(val input: String, val shiftAmount: Int): Gate() {
    override fun doResolve(gateLookup: Map<String, Gate>): Int {
        return gateLookup[input]!!.resolve(gateLookup).shr(shiftAmount)
    }
}

data class ProxyValueGate(val proxy: String): Gate() {
    override fun doResolve(gateLookup: Map<String, Gate>): Int {
        return gateLookup[proxy]!!.resolve(gateLookup)
    }
}

data class RawValueGate(val value: Int): Gate() {
    override fun doResolve(gateLookup: Map<String, Gate>): Int {
        return value
    }
}

fun getInitWires(): MutableMap<String, Gate> {
    val p7_input = getPuzzleInput(7, 1)
    val wireValues = mutableMapOf<String, Gate>()
    val componentRegex = "^(?:(?:(?<left>\\w+) )?(?<op>AND|OR|NOT|[LR]SHIFT) )?(?<right>\\w+|\\d+) -> (?<outWire>\\w+)$".toRegex()
    for (l in p7_input.split("\n")) {
        val matches = componentRegex.find(l) ?: throw IllegalArgumentException("Cannot match $l")
        wireValues[matches.groups["outWire"]!!.value] = when (matches.groups["op"]?.value) {
            "AND" -> AndGate(matches.groups["left"]!!.value, matches.groups["right"]!!.value)
            "OR" -> OrGate(matches.groups["left"]!!.value, matches.groups["right"]!!.value)
            "NOT" -> NotGate(matches.groups["right"]!!.value)
            "LSHIFT" -> LShiftGate(matches.groups["left"]!!.value, matches.groups["right"]!!.value.toInt())
            "RSHIFT" -> RShiftGate(matches.groups["left"]!!.value, matches.groups["right"]!!.value.toInt())
            null -> {
                val r = matches.groups["right"]!!.value
                if (r.matches("\\d+".toRegex())) {
                    RawValueGate(r.toInt())
                } else {
                    ProxyValueGate(r)
                }
            }
            else -> throw IllegalArgumentException("Unable to handle op ${matches.groups["op"]?.value}")
        }
    }
    return wireValues
}

fun main() {
    var wireValues = getInitWires()

    val valueOfA = wireValues["a"]!!.resolve(wireValues)
    println("The value on wire A is $valueOfA")
    wireValues = getInitWires()
    wireValues["b"] = RawValueGate(valueOfA)
    val updatedValueOfA = wireValues["a"]!!.resolve(wireValues)
    println("The updated value on wire A is $updatedValueOfA")
}