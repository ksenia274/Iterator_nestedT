fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
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
