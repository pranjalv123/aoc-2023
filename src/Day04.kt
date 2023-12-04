class Card(val myNumbers: List<Int>, val winners: List<Int>) {
    companion object {
        fun ofString(line: String): Card {
            val (myNumbers, winners) = line.split(":")[1].split("|")
                .map { it.trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt() } }
            return Card(myNumbers, winners)
        }
    }

    val overlap = myNumbers.toSet().intersect(winners.toSet()).size
    val score = if (overlap == 0) 0 else 1.shl(overlap - 1)
}

fun main() {
    fun part1(input: List<String>): Int = input.map { line ->
        Card.ofString(line).score
    }.sum()


    fun part2(input: List<String>): Int {
        val cards = input.map { Card.ofString(it) }
        val cardCounts = cards.foldIndexed(cards.map { Pair(it, 1) }.toMap()) { i, counts, card ->
            val score = card.overlap.also { println(it) }
            val thisCounts =
                (1..score).mapNotNull { cards.getOrNull(i + it)?.let { Pair(it, counts.getOrDefault(card, 0)) } }
                    .toMap()
            (counts + thisCounts).mapValues { thisCounts.getOrDefault(it.key, 0) + counts.getOrDefault(it.key, 0) }
        }
        return cardCounts.map { it.value }.sum()
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")

    check(part2(testInput).also { println(it) } == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
