package eu.majortanya.naturalsort

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.IOException

@RunWith(Parameterized::class)
class InputOutputTest(
    inputResource: String,
    expectedOutputResource: String,
    private val comparator: Comparator<String>,
) {
    private val input: List<String> = load(inputResource)
    private val expectedOutput: List<String> = load(expectedOutputResource)

    @Test
    fun testInputOutput() {
        val items: List<String> = input.sortedWith(comparator)
        Assert.assertEquals(expectedOutput, items)
    }

    companion object {
        @Throws(IOException::class)
        private fun load(resourceName: String): List<String> {
            val resourceStream = object {}::class.java.getResourceAsStream(resourceName)
                ?: throw IOException("Resource not found: $resourceName")

            val result: MutableList<String> = mutableListOf()
            resourceStream.reader().buffered().use { reader ->
                reader.forEachLine { line ->
                    if (!line.startsWith("#")) result.add(line)
                }
            }
            return result
        }

        @Parameterized.Parameters
        @JvmStatic
        fun data(): Iterable<Array<Any>> {
            val testcases = arrayOf(
                arrayOf("/alphanum_test_1_input.txt", "/alphanum_test_1_output.txt"),
                arrayOf("/alphanum_test_2_input.txt", "/alphanum_test_2_output.txt"),
            )
            val comparators = listOf<Comparator<String>>(
                SimpleNaturalComparator.getInstance(),
                CaseInsensitiveSimpleNaturalComparator.getInstance(),
            )

            return buildList(testcases.size * comparators.size) {
                for (testcase in testcases) {
                    assert(testcase.size == 2)
                    for (comparator in comparators) {
                        add(arrayOf(testcase[0], testcase[1], comparator))
                    }
                }
            }
        }
    }
}
