package eu.majortanya.naturalsort

import kotlin.math.sign

enum class ExpectedTestResult(private val resultName: String, private val signum: Int) {
    LESS_THAN("less than", -1),
    GREATER_THAN("greater than", 1),
    EQUALS("equal to", 0);

    fun verify(comparisonResult: Int): Boolean {
        return sign(signum.toDouble()) == sign(comparisonResult.toDouble())
    }

    override fun toString(): String {
        return resultName
    }
}
