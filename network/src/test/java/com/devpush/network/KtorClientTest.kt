package com.devpush.network

import com.devpush.network.models.domain.Character
import com.devpush.network.models.domain.CharacterGender
import com.devpush.network.models.domain.CharacterStatus
import com.devpush.network.models.domain.Episode
import io.ktor.client.engine.mock.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.*
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for KtorClient
 * Note: These tests verify the logic and error handling.
 * Full integration tests with mock HTTP responses would require additional setup.
 */
class KtorClientTest {

    private lateinit var ktorClient: KtorClient

    @Before
    fun setup() {
        ktorClient = KtorClient()
    }

    @Test
    fun `ApiOperation Success should be created for successful operations`() = runTest {
        val testData = "test"
        val operation = KtorClient.ApiOperation.Success(testData)
        
        assertTrue(operation is KtorClient.ApiOperation.Success)
        assertEquals(testData, operation.data)
    }

    @Test
    fun `ApiOperation Failure should be created for failed operations`() = runTest {
        val exception = Exception("Network error")
        val operation = KtorClient.ApiOperation.Failure<String>(exception)
        
        assertTrue(operation is KtorClient.ApiOperation.Failure)
        assertEquals(exception, operation.exception)
    }

    @Test
    fun `ApiOperation onSuccess should chain correctly`() = runTest {
        var result: String? = null
        val operation = KtorClient.ApiOperation.Success("test")
        
        operation.onSuccess { data ->
            result = data
        }
        
        assertEquals("test", result)
    }

    @Test
    fun `ApiOperation onFailure should chain correctly`() = runTest {
        var errorMessage: String? = null
        val exception = Exception("Test error")
        val operation = KtorClient.ApiOperation.Failure<String>(exception)
        
        operation.onFailure { error ->
            errorMessage = error.message
        }
        
        assertEquals("Test error", errorMessage)
    }

    @Test
    fun `ApiOperation mapSuccess should transform Success data`() = runTest {
        val operation = KtorClient.ApiOperation.Success(10)
        val mapped = operation.mapSuccess { it.toString() }
        
        assertTrue(mapped is KtorClient.ApiOperation.Success)
        assertEquals("10", (mapped as KtorClient.ApiOperation.Success).data)
    }

    @Test
    fun `ApiOperation mapSuccess should preserve Failure`() = runTest {
        val exception = Exception("Error")
        val operation = KtorClient.ApiOperation.Failure<Int>(exception)
        val mapped = operation.mapSuccess { it.toString() }
        
        assertTrue(mapped is KtorClient.ApiOperation.Failure)
        assertEquals(exception, (mapped as KtorClient.ApiOperation.Failure).exception)
    }

    @Test
    fun `ApiOperation chaining onSuccess and onFailure should work correctly`() = runTest {
        var successCalled = false
        var failureCalled = false
        
        val operation = KtorClient.ApiOperation.Success("data")
        
        operation
            .onSuccess { successCalled = true }
            .onFailure { failureCalled = true }
        
        assertTrue(successCalled)
        assertFalse(failureCalled)
    }

    @Test
    fun `ApiOperation chaining with Failure should only call onFailure`() = runTest {
        var successCalled = false
        var failureCalled = false
        
        val operation = KtorClient.ApiOperation.Failure<String>(Exception("error"))
        
        operation
            .onSuccess { successCalled = true }
            .onFailure { failureCalled = true }
        
        assertFalse(successCalled)
        assertTrue(failureCalled)
    }

    @Test
    fun `mapSuccess can be chained multiple times`() = runTest {
        val operation = KtorClient.ApiOperation.Success(5)
        
        val result = operation
            .mapSuccess { it * 2 }
            .mapSuccess { it + 10 }
            .mapSuccess { it.toString() }
        
        assertTrue(result is KtorClient.ApiOperation.Success)
        assertEquals("20", (result as KtorClient.ApiOperation.Success).data)
    }

    @Test
    fun `complex chaining with mapSuccess and onSuccess should work`() = runTest {
        var finalValue: String? = null
        
        KtorClient.ApiOperation.Success(5)
            .mapSuccess { it * 2 }
            .mapSuccess { it.toString() }
            .onSuccess { finalValue = it }
        
        assertEquals("10", finalValue)
    }
}
