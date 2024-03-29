package com.example.terminal2020_shenaijia2018110532.ui.dashboard.model

class Card(private var suit: String, private var rank: String, var isChosen:Boolean = false, var isMatched:Boolean = false)  {
    companion object {
        val rankStrings= arrayOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
        val validSuits = arrayOf("♥", "♦", "♠", "♣")
    }

    override fun toString(): String {
        return suit + rank
    }

    fun match(otherCards: Array<Card>): Int {
        var score = 0
        if (otherCards.size == 1) {
            val otherCard = otherCards[0]
            if (otherCard.rank == rank) {
                score = 1
            } else if (otherCard.suit == suit) {
                score = 4
            }
        }
        return score
    }
}