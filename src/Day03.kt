fun main() {


    fun get(input: List<String>, x: Int, y: Int): Char? {
        if (x < 0 || y < 0 || y >= input.size || x >= input[y].length) return null
        return input[y][x]
    }

    fun bordersSymbol(input: List<String>, x: Int, y: Int): Boolean {
        return listOf(
            get(input, x - 1, y - 1),
            get(input, x, y - 1),
            get(input, x + 1, y - 1),
            get(input, x - 1, y),
            get(input, x + 1, y),
            get(input, x - 1, y + 1),
            get(input, x, y + 1),
            get(input, x + 1, y + 1),
        ).also { println(it) }.any { it != null && it != '.' && !it.isDigit() }
    }

    fun getI(input: List<String>, x: Int, y: Int): Triple<Int, Int, Char?> {
        if (x < 0 || y < 0 || y >= input.size || x >= input[y].length) return Triple(x, y, null)
        return Triple(x, y, input[y][x])
    }

    fun borderedGears(input: List<String>, x: Int, y: Int): List<Pair<Int, Int>> {
        return listOf(
            getI(input, x - 1, y - 1),
            getI(input, x, y - 1),
            getI(input, x + 1, y - 1),
            getI(input, x - 1, y),
            getI(input, x + 1, y),
            getI(input, x - 1, y + 1),
            getI(input, x, y + 1),
            getI(input, x + 1, y + 1),
        ).filter { it.third == '*' }.map { it.first to it.second }
    }


    fun part1(input: List<String>): Int {
        val numbers = input.flatMapIndexed { i, it -> "[0-9]+".toRegex().findAll(it).map { it to i } }

        return numbers.filter { (match, i) ->
            match.range.map { bordersSymbol(input, it, i) }.any { it }
        }.map {
            it.first.value.toInt()
        }.also { println(it) }.sum()
    }


    fun part2(input: List<String>): Int {
        val numbers = input.flatMapIndexed { i, it -> "[0-9]+".toRegex().findAll(it).map { it to i } }
        val gears = numbers.flatMap { (match, i) ->
            match.range.flatMap { charX ->
                borderedGears(input, charX, i).map { gear ->
                    gear to match.value.toInt()
                }
            }.toSet()
        }
        println(gears)
        val gearNums = gears.groupBy({ it.first }, { it.second }).also { println(it) }.filterValues { it.size == 2 }
            .mapValues { it.value.fold(1) { acc, i -> acc * i } }.values.sum().also { println(it) }
        return gearNums
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
