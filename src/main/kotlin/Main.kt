fun main() {
    val nestedList = listOf(1, 2, listOf(3, 4, listOf(5, 6)), 7, listOf(8, 9))
    val nested = Nested<Int>(nestedList)
    for (item in nested) {
        println(item)
    }
}

class Nested<T>(private val list: List<Any?>) : Iterable<T> {
    override fun iterator(): Iterator<T> {
        return NestedIterator(list.iterator()) as Iterator<T>
    }

    private class NestedIterator(private var iterator: Iterator<Any?>) : Iterator<Any?> {
        private var currentIterator: NestedIterator? = null
        private var currentItem: Any? = null

        override fun hasNext(): Boolean {
            while (currentItem == null && iterator.hasNext()) {
                val next = iterator.next()
                if (next is List<*>) {
                    currentIterator = NestedIterator(next.iterator())
                    currentItem = currentIterator?.next()
                } else {
                    currentItem = next
                }
            }
            return currentItem != null
        }

        override fun next(): Any {
            if (!hasNext()) throw NoSuchElementException()
            val result = currentItem
            currentItem = null
            if (currentIterator?.hasNext() == true) {
                currentItem = currentIterator?.next()
            }
            return result!!
        }
    }
}
