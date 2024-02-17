package eu.majortanya.naturalsort

import org.junit.Assert
import org.junit.Test

abstract class AbstractSimpleNaturalComparatorTest internal constructor(
    private val string1: String,
    private val expectedRelation: ExpectedTestResult,
    private val string2: String,
    private val comparator: Comparator<String>
) {
    @Test
    fun testSimpleNaturalComparator() {
        val c = comparator.compare(string1, string2)
        Assert.assertTrue(
            "Expected '$string1' to be $expectedRelation '$string2', but was: $c",
            expectedRelation.verify(c),
        )
    }

    companion object {
        val defaultTestCases: Collection<Array<Any>> = listOf(
            arrayOf("z2.doc", ExpectedTestResult.LESS_THAN, "z10.doc"),
            arrayOf("z010.doc", ExpectedTestResult.EQUALS, "z10.doc"),
            arrayOf("z10", ExpectedTestResult.LESS_THAN, "z10a"),
            arrayOf("z10a", ExpectedTestResult.GREATER_THAN, "z10"),
            arrayOf("a", ExpectedTestResult.LESS_THAN, "z"),
            arrayOf("10", ExpectedTestResult.LESS_THAN, "z"),
            arrayOf("z", ExpectedTestResult.GREATER_THAN, "10"),
            arrayOf("10", ExpectedTestResult.LESS_THAN, "20"),
            arrayOf("10", ExpectedTestResult.GREATER_THAN, "5"),
            arrayOf("10", ExpectedTestResult.EQUALS, "10"),
            arrayOf("9223372036854775806", ExpectedTestResult.LESS_THAN, "9223372036854775807"),
            arrayOf("18446744073709551614", ExpectedTestResult.LESS_THAN, "18446744073709551615")
        )
    }
}
