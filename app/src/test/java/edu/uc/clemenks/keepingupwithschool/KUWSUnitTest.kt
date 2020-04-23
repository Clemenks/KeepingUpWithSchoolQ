package edu.uc.clemenks.keepingupwithschool

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TestRule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class KUWSUnitTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
