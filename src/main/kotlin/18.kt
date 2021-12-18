open class LightGrid(val grid: List<List<Boolean>>) {
    val w = grid[0].size
    val h = grid.size

    open operator fun get(i: Int, j: Int) = grid[i][j]

    fun neighbours(i: Int, j: Int) = sequence {
        if (i > 0 && j > 0) yield(this@LightGrid[i - 1, j - 1])
        if (i > 0) yield(this@LightGrid[i - 1, j])
        if (i > 0 && j < w - 1) yield(this@LightGrid[i - 1, j + 1])
        if (j > 0) yield(this@LightGrid[i, j - 1])
        if (j < w - 1) yield(this@LightGrid[i, j + 1])
        if (i < h - 1 && j > 0) yield(this@LightGrid[i + 1, j - 1])
        if (i < h - 1) yield(this@LightGrid[i + 1, j])
        if (i < h - 1 && j < w - 1) yield(this@LightGrid[i + 1, j + 1])
    }

    open fun step() = LightGrid((0 until h).map { i ->
        (0 until w).map { j ->
            (this[i, j] && neighbours(i, j).count { it } in 2..3) ||
                    (!this[i, j] && neighbours(i, j).count { it } == 3)
        }
    })
}

class BrokenCornerLightGrid(grid: List<List<Boolean>>): LightGrid(grid) {
    override operator fun get(i: Int, j: Int) = when(i to j) {
        0 to 0 -> true
        0 to w - 1 -> true
        h - 1 to 0 -> true
        h - 1 to w - 1 -> true
        else -> super.get(i, j)
    }

    override fun step() = BrokenCornerLightGrid((0 until h).map { i ->
        (0 until w).map { j ->
            (this[i, j] && neighbours(i, j).count { it } in 2..3) ||
                    (!this[i, j] && neighbours(i, j).count { it } == 3)
        }
    })
}

fun main() {
    val initialGrid = LightGrid(getPuzzleInput(18, 1).split("\n").map { it.map { it == '#' } })
    val grid100 = (1..100).fold(initialGrid) { acc, i -> acc.step() }
    println(grid100.grid.sumOf { it.count { it } })
    val brokenGrid100 = (1..100).fold(BrokenCornerLightGrid(initialGrid.grid)) { acc, i -> acc.step() }
    println(brokenGrid100.grid
        .flatMapIndexed { i , booleans -> booleans.mapIndexed{j, bool -> brokenGrid100[i, j]} }.count { it })
}