import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class NestedTest {
    @Test
    fun testNestedIterator() {
        val list = listOf(1, listOf(2, 3), listOf(4, listOf(5, 6)))
        val nested = Nested<Int>(list)

        val expectedList = listOf(1, 2, 3, 4, 5, 6)
        val actualList = mutableListOf<Int>()
        for (item in nested) {
            actualList.add(item)
        }

        assertEquals(expectedList, actualList)
    }

    @Test
    fun testEmptyList() {
        val list = emptyList<Any>()
        val nested = Nested<Int>(list)

        assertFalse(nested.iterator().hasNext())
    }

    @Test
    fun testFlatList() {
        val list = listOf(1, 2, 3)
        val nested = Nested<Int>(list)

        val expectedList = listOf(1, 2, 3)
        val actualList = mutableListOf<Int>()
        for (item in nested) {
            actualList.add(item)
        }

        assertEquals(expectedList, actualList)
    }

    @Test
    fun testNestedWithStrings() {
        val list = listOf("hello", "world", listOf("nested", "list"), "!")
        val nested = Nested<String>(list)
        val expected = listOf("hello", "world", "nested", "list", "!")
        val actual = nested.toList()
        assertEquals(expected, actual)
    }

    @Test
    fun testNestedWithBooleans() {
        val list = listOf(true, false, listOf(true, listOf(false)), true)
        val nested = Nested<Boolean>(list)
        val expected = listOf(true, false, true, false, true)
        val actual = nested.toList()
        assertEquals(expected, actual)
    }

    @Test
    fun testNestedWithMixedTypes() {
        val list = listOf(1, "hello", null, listOf(true, false))
        val nested = Nested<Any?>(list)
        val expected = listOf(1, "hello", true, false)
        val actual = nested.filterNotNull().toList()
        assertEquals(expected, actual)
    }

    @Test
    fun testNestedWithLargeList() {
        val list = (1..100000).toList()
        val nested = Nested<Int>(list)
        val expected = (1..100000).toList()
        val actual = nested.toList()
        assertEquals(expected, actual)
    }

}