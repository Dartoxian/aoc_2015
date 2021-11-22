fun paperRequiredForBox(l: Int, w: Int, h: Int): Int {
    val sides = listOf(l * w, w * h, h * l)
    return (sides.sum() * 2) + sides.minOrNull()!!
}

fun ribbonRequiredForBox(l: Int, w: Int, h: Int): Int {
    val volume = l * w * h;
    val shortedPerimeter = listOf(l, w, h).sorted().slice(0..1).sum() * 2
    return volume + shortedPerimeter
}

fun main() {
    val p2_input = getPuzzleInput(2, 1);
    var totalPaper = 0;
    var totalRibbon = 0;
    for (l in p2_input.split("\n")) {
        val boxDimensions = l.split("x").map { it.toInt() }
        totalPaper += paperRequiredForBox(boxDimensions[0], boxDimensions[1], boxDimensions[2])
        totalRibbon += ribbonRequiredForBox(boxDimensions[0], boxDimensions[1], boxDimensions[2]);
    }
    println("The elves need $totalPaper sqrft of paper")
    println("The elves need $totalRibbon ft of ribbon")
}