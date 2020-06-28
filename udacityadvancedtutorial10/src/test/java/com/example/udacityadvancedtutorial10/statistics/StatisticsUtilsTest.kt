package com.example.udacityadvancedtutorial10.statistics

import com.example.udacityadvancedtutorial10.data.Task
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test


class StatisticsUtilsTest {

    /**
     * TEST FUNCTION NAMING CONVENTIONS
     * subjectUnderTest_actionOrInput_resultState
     */

    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsZeroHundred() {
        // GIVEN
        val tasks = listOf<Task>(
            Task("title", "description", isCompleted = false)
        )

        // WHEN
        val result = getActiveAndCompletedStats(tasks)

        // THEN
        // assertEquals(0f, result.completedTasksPercent)
        // assertEquals(100f, result.activeTasksPercent)
        // Alternate demonstration - with assertion framework - hamcrest
        assertThat(result.completedTasksPercent, `is`(0f))
        assertThat(result.activeTasksPercent, `is`(100f))
    }

    @Test
    fun getActiveAndCompletedStats_empty_returnsZeroHundred() {
        // GIVEN
        val tasks = emptyList<Task>()

        // WHEN
        val result = getActiveAndCompletedStats(tasks)

        // THEN
        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)
    }

    @Test
    fun getActiveAndCompletedStats_null_returnsZeroHundred() {
        // GIVEN
        val tasks = null

        // WHEN
        val result = getActiveAndCompletedStats(tasks)

        // THEN
        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)
    }

    @Test
    fun getActiveAndCompletedStats_both_returnsFortySixty() {
        val tasks = listOf<Task>(
            Task("title", "description", isCompleted = true),
            Task("title", "description", isCompleted = true),
            Task("title", "description", isCompleted = false),
            Task("title", "description", isCompleted = false),
            Task("title", "description", isCompleted = false)
        )

        val result = getActiveAndCompletedStats(tasks)

        assertEquals(40f, result.completedTasksPercent)
        assertEquals(60f, result.activeTasksPercent)
    }

}