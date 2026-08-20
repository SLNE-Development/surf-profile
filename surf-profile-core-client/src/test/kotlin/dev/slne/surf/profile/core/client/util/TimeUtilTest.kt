package dev.slne.surf.profile.core.client.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TimeUtilTest {
    @Test
    fun `formats seconds only below a minute`() {
        assertEquals("0s", 0L.formatSeconds())
        assertEquals("59s", 59L.formatSeconds())
    }

    @Test
    fun `formats minutes with padded seconds`() {
        assertEquals("1m 00s", 60L.formatSeconds())
        assertEquals("59m 59s", 3599L.formatSeconds())
    }

    @Test
    fun `formats hours with padded minutes and seconds`() {
        assertEquals("1h 00m 00s", 3600L.formatSeconds())
        assertEquals("2h 03m 04s", (2 * 3600 + 3 * 60 + 4).toLong().formatSeconds())
    }

    @Test
    fun `keeps hours unpadded beyond a day`() {
        assertEquals("25h 00m 00s", (25 * 3600).toLong().formatSeconds())
    }
}
