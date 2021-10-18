package com.enrech.core.utils

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.robolectric.util.ReflectionHelpers

@Suppress("DEPRECATION")
class ConnectivityHandlerImplTest {

    private val connectivityManager: ConnectivityManager = mockk(relaxed = true)
    private lateinit var sut: ConnectivityHandler

    @Before
    fun setUp() {
        sut = ConnectivityHandlerImpl(connectivityManager)
    }

    @Test
    fun `given build version greater or equal M and non active network, expect no connection`() {
        //ARRANGE
        mockVersion(greaterOrEqualM = true)
        every { connectivityManager.activeNetwork } returns null

        //ACT
        val result = sut.isNetworkAvailable()

        //ASSERT
        assertFalse(result)
    }

    @Test
    fun `given build version greater or equal M and no network capabilities, expect no connection`() {
        //ARRANGE
        mockVersion(greaterOrEqualM = true)
        every { connectivityManager.activeNetwork } returns mockk(relaxed = true)
        every { connectivityManager.getNetworkCapabilities(any()) } returns null

        //ACT
        val result = sut.isNetworkAvailable()

        //ASSERT
        assertFalse(result)
    }

    @Test
    fun `given build version greater or equal M and active wifi, expect connection`() {
        testActualNetworkTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

    @Test
    fun `given build version greater or equal M and active cellular, expect connection`() {
        testActualNetworkTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }

    @Test
    fun `given build version greater or equal M and active ethernet, expect connection`() {
        testActualNetworkTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    @Test
    fun `given build version greater or equal M and active vpn, expect connection`() {
        testActualNetworkTransport(NetworkCapabilities.TRANSPORT_VPN)
    }

    @Test
    fun `given build version greater or equal M and non internet related transport active, expect no connection`() {
        //ARRANGE
        mockVersion(greaterOrEqualM = true)
        every { connectivityManager.activeNetwork } returns mockk(relaxed = true)
        every { connectivityManager.getNetworkCapabilities(any()) } returns mockk(relaxed = true)

        //ACT
        val result = sut.isNetworkAvailable()

        //ASSERT
        assertFalse(result)
    }

    @Test
    fun `given build version less than M and non active network, expect no connection`() {
        //ARRANGE
        mockVersion(greaterOrEqualM = false)
        every { connectivityManager.activeNetworkInfo } returns null

        //ACT
        val result = sut.isNetworkAvailable()

        //ASSERT
        assertFalse(result)
    }

    @Test
    fun `given build version less than M and active wifi, expect connection`() {
        testLegacyNetworkTransport(ConnectivityManager.TYPE_WIFI)
    }

    @Test
    fun `given build version less than M and active mobile network, expect connection`() {
        testLegacyNetworkTransport(ConnectivityManager.TYPE_MOBILE)
    }

    @Test
    fun `given build version less than M and active ethernet, expect connection`() {
        testLegacyNetworkTransport(ConnectivityManager.TYPE_ETHERNET)
    }

    @Test
    fun `given build version less than M and active vpn, expect connection`() {
        testLegacyNetworkTransport(ConnectivityManager.TYPE_VPN)
    }

    @Test
    fun `given build version less than M and non internet related type, expect no connection`() {
        //ARRANGE
        mockVersion(greaterOrEqualM = false)
        every { connectivityManager.activeNetworkInfo } returns mockk(relaxed = true) {
            every { type } returns ConnectivityManager.TYPE_WIMAX
        }

        //ACT
        val result = sut.isNetworkAvailable()

        //ASSERT
        assertFalse(result)
    }

    private fun mockVersion(greaterOrEqualM: Boolean) {
        ReflectionHelpers.setStaticField(
            Build.VERSION::class.java,
            "SDK_INT",
            if (greaterOrEqualM) Build.VERSION_CODES.M else Build.VERSION_CODES.LOLLIPOP
        )
    }

    private fun testActualNetworkTransport(transport: Int) {
        //ARRANGE
        mockVersion(greaterOrEqualM = true)
        every { connectivityManager.activeNetwork } returns mockk(relaxed = true)
        every { connectivityManager.getNetworkCapabilities(any()) } returns mockk(relaxed = true) {
            every { hasTransport(transport) } returns true
        }

        //ACT
        val result = sut.isNetworkAvailable()

        //ASSERT
        assertTrue(result)
    }

    private fun testLegacyNetworkTransport(transport: Int) {
        //ARRANGE
        mockVersion(greaterOrEqualM = false)
        every { connectivityManager.activeNetworkInfo } returns mockk(relaxed = true) {
            every { type } returns transport
        }

        //ACT
        val result = sut.isNetworkAvailable()

        //ASSERT
        assertTrue(result)
    }


}