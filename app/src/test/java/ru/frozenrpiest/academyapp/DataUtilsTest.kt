package ru.frozenrpiest.academyapp

import junit.framework.TestCase

class DataUtilsTest : TestCase() {

    fun testRoundRating() {
        assertTrue(DataUtils.roundRating(3.3f) == 3.5f)
    }
}