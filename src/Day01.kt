fun main() {

    val digitsmap = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )
    val digitsIndivRegex = digitsmap.keys.map { it.toRegex() }

    fun part1(input: List<String>): Int {

        val vals = input.map {
            it.filter { it.isDigit() }
        }.also { println(it) }.map { it.first().digitToInt() * 10 + it.last().digitToInt() }

        return vals.sum()
    }


    fun part2(input: List<String>): Int {
        val vals = input.map { str ->
            val writtenDigits = digitsIndivRegex.flatMap { it.findAll(str) }
            println(str)
            writtenDigits.fold(str) { s, match ->
                s.replaceRange(
                    match.range.first, match.range.first + 1, digitsmap[match.value].toString()
                )
            }.also { println(it) }.filter { it.isDigit() }
                .let { it.first().digitToInt() * 10 + it.last().digitToInt() }
        }
        return vals.sum()
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
