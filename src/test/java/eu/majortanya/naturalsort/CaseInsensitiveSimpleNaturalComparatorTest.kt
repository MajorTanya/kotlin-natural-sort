package eu.majortanya.naturalsort

import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class CaseInsensitiveSimpleNaturalComparatorTest(
    string1: String,
    expectedRelation: ExpectedTestResult,
    string2: String,
) : AbstractSimpleNaturalComparatorTest(string1, expectedRelation, string2, COMPARATOR) {
    companion object {
        private val COMPARATOR: Comparator<String> = CaseInsensitiveSimpleNaturalComparator.getInstance()

        @Parameterized.Parameters
        @JvmStatic
        fun data(): Iterable<Array<Any>> {
            val result = defaultTestCases.toMutableList()
            result.add(arrayOf("a", ExpectedTestResult.EQUALS, "A"))
            return result
        }
    }
}
