package eu.majortanya.naturalsort

/**
 * The same as [SimpleNaturalComparator] but comparison is done after converting each
 * character to lower case.
 */
class CaseInsensitiveSimpleNaturalComparator<T : CharSequence>
private constructor() : AbstractSimpleNaturalComparator<T>(), Comparator<T> {

    override fun compareChars(c1: Char, c2: Char): Int = c1.lowercaseChar() - c2.lowercaseChar()

    companion object {
        private val INSTANCE: Comparator<CharSequence> = CaseInsensitiveSimpleNaturalComparator()

        @Suppress("UNCHECKED_CAST") // safe cast because T is only ever CharSequence or subtype
        fun <T : CharSequence> getInstance(): Comparator<T> = INSTANCE as Comparator<T>
    }
}
