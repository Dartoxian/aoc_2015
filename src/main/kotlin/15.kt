import kotlin.math.max

data class Ingredient(
    val name: String,
    val capacity: Int,
    val durability: Int,
    val flavor: Int,
    val texture: Int,
    val calories: Int
)

val ingredientPattern =
    "(\\w+): capacity (-?\\d+), durability (-?\\d+), flavor (-?\\d+), texture (-?\\d+), calories (-?\\d+)".toRegex()

fun String.toIngredient() = with(ingredientPattern.find(this) ?: throw IllegalArgumentException("Cannot parse $this")) {
    Ingredient(
        this.groupValues[1],
        this.groupValues[2].toInt(),
        this.groupValues[3].toInt(),
        this.groupValues[4].toInt(),
        this.groupValues[5].toInt(),
        this.groupValues[6].toInt(),
    )
}

data class Recipe(val quantities: Map<Ingredient, Int>) {
    fun score() =
        max(0, quantities.entries.sumOf { it.key.capacity * it.value }) *
        max(0, quantities.entries.sumOf { it.key.flavor * it.value }) *
        max(0, quantities.entries.sumOf { it.key.durability * it.value }) *
        max(0, quantities.entries.sumOf { it.key.texture * it.value })
    fun calories() = quantities.entries.sumOf { it.key.calories * it.value }
}

fun main() {
    val ingredients = getPuzzleInput(15, 1).split("\n").map(String::toIngredient)
    fun enumerateAllRecipes(withIngredients: List<Ingredient>, totalQuantity: Int): Sequence<Recipe> = sequence {
        if (withIngredients.size == 1) {
            yield(Recipe(mapOf(withIngredients[0] to totalQuantity)))
        } else {
            for (i in 0..totalQuantity) {
                for (r in enumerateAllRecipes(withIngredients.slice(1 until withIngredients.size), totalQuantity - i)) {
                    yield(Recipe(r.quantities + (withIngredients[0] to i)))
                }
            }
        }
    }
    val bestRecipe = enumerateAllRecipes(ingredients, 100).maxByOrNull { it.score() }!!
    println("Best recipe score ${bestRecipe.score()}")

    val best500Recipe = enumerateAllRecipes(ingredients, 100)
        .filter { it.calories() == 500 }
        .maxByOrNull { it.score() }!!
    println("Best 500 calories recipe score ${best500Recipe.score()}")
}