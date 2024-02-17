package eu.majortanya.naturalsort

/**
 * Compares Strings (or any other CharSequence subclass) using the
 * [natural sort](http://blog.codinghorror.com/sorting-for-humans-natural-sort-order/) /
 * [alphanum algorithm](http://www.davekoelle.com/alphanum.html) which gives a more
 * "natural" ordering when presenting the sorted list of strings to humans.
 *
 *
 *
 * This is a fast implementation of the original algorithm which produces no garbage during its run.
 * There is also a case-insensitive variant you might want to use:
 * [CaseInsensitiveSimpleNaturalComparator]. This is a fully self-contained implementation
 * compiled with Java 1.6 for maximum compatibility which does not add any additional dependencies
 * to your project.
 *
 *
 *
 * There are still limitations of this implementation to be aware of (which hopefully will be
 * addressed in future releases):
 *
 *
 *  * Does not play nice with Unicode, especially characters which are outside the BMP (i.e.
 * codepoints with values larger than [Character.MAX_VALUE]).
 *  * Does not handle fractions or grouping characters properly.
 *  * Only understands integer values up to 2^64-1.
 *
 */
class SimpleNaturalComparator<T : CharSequence>
private constructor() : AbstractSimpleNaturalComparator<T>(), Comparator<T> {
    override fun compareChars(c1: Char, c2: Char): Int = c1.code - c2.code

    companion object {
        private val INSTANCE: Comparator<CharSequence> = SimpleNaturalComparator()

        @Suppress("UNCHECKED_CAST") // safe cast because T is only ever CharSequence or subtype
        fun <T : CharSequence> getInstance(): Comparator<T> = INSTANCE as Comparator<T>
    }
}
