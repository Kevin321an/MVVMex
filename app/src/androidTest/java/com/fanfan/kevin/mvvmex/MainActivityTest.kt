

package com.fanfan.kevin.mvvmex

import android.app.Activity
import android.content.Intent


import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4

import com.fanfan.kevin.mvvmex.UI.main.MainActivity

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat

/**
 * Simply sanity test to ensure that activity launches without any issues and shows some data.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    var testRule = CountingTaskExecutorRule()

    @Test
    @Throws(InterruptedException::class, TimeoutException::class)
    fun showSomeResults() {
        val intent = Intent(InstrumentationRegistry.getTargetContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val activity = InstrumentationRegistry.getInstrumentation().startActivitySync(intent)
        testRule.drainTasks(10, TimeUnit.SECONDS)
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        val recyclerView = activity.findViewById<RecyclerView>(R.id.carList)
        waitForAdapterChange(recyclerView)
        assertThat(recyclerView.adapter, notNullValue())
        waitForAdapterChange(recyclerView)
        assertThat(recyclerView.adapter!!.itemCount > 0, `is`(true))
    }

    @Throws(InterruptedException::class)
    private fun waitForAdapterChange(recyclerView: RecyclerView) {
        val latch = CountDownLatch(1)
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            recyclerView.adapter!!.registerAdapterDataObserver(
                object : RecyclerView.AdapterDataObserver() {
                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                        latch.countDown()
                    }
                    override fun onChanged() {
                        latch.countDown()
                    }
                })
        }
        if (recyclerView.adapter!!.itemCount > 0) {
            return //already loaded
        }
        assertThat(latch.await(10, TimeUnit.SECONDS), `is`(true))
    }
}
