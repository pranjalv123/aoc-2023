class Game(val index: Int, val rounds: List<Round>) {

    data class Round(val red: Int, val blue: Int, val green: Int) {
        val max_red = 12
        val max_green = 13
        val max_blue = 14

        fun isPossible(): Boolean = red <= max_red && blue <= max_blue && green <= max_green

        constructor(
            str: String,
            rbg: Map<String, Int> = str.split(",").map { it.trim().split(" ") }.map { it[1] to it[0].toInt() }.toMap(),
        ) : this(rbg["red"] ?: 0, rbg["blue"] ?: 0, rbg["green"] ?: 0)
    }

    constructor(
        line: String,
        rounds: List<Round> = line.split(":")[1].trim().split(";", ",").map { Round(it.trim()) },
        index: Int = line.split(":")[0].split(" ")[1].trim().toInt(),
    ) : this(index, rounds)

    fun minPossiblePower() : Int {
        val minRed = rounds.maxOfOrNull { it.red } ?: 0
        val minBlue = rounds.maxOfOrNull { it.blue } ?: 0
        val minGreen = rounds.maxOfOrNull { it.green } ?: 0
        return minRed * minBlue * minGreen
    }
}

fun main() {


    fun part1(input: List<String>): Int =
        input.map { Game(it) }.filter { it.rounds.all { it.isPossible() } }.map { it.index }.sum()


    fun part2(input: List<String>): Int =
        input.map { Game(it).minPossiblePower() }.sum()


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
