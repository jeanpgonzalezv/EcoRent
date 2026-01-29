package com.duoc.ecorentfinal

import org.junit.Assert.*
import org.junit.Test

class ViewModelTest {

    @Test
    fun testSumaBasica() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testComparaciones() {
        assertTrue(10 > 5)
        assertFalse(5 > 10)
    }
}