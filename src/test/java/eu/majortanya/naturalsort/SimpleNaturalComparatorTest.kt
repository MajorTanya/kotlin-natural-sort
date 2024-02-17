package eu.majortanya.naturalsort

import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class SimpleNaturalComparatorTest(
    string1: String,
    expectedRelation: ExpectedTestResult,
    string2: String,
) : AbstractSimpleNaturalComparatorTest(string1, expectedRelation, string2, COMPARATOR) {
    companion object {
        private val COMPARATOR: Comparator<String> = SimpleNaturalComparator.getInstance()

        @Parameterized.Parameters
        @JvmStatic
        fun data(): Iterable<Array<Any>> = defaultTestCases
    }
}
