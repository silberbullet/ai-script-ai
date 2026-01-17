package club.auth.service

import club.auth.exception.AuthErrorCode.AUTH_REFRESH_TOKEN_NOT_FOUND
import club.auth.exception.AuthException
import club.auth.port.AuthRedisPort
import club.auth.port.GenerateTokenPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import java.time.Duration

class AuthTokenServiceTest : FreeSpec({

    val authRedisPort = mockk<AuthRedisPort>()
    val generateTokenPort = mockk<GenerateTokenPort>()

    val sut = AuthTokenService(authRedisPort, generateTokenPort)

    beforeTest { clearAllMocks() }

    "토큰발급(issueToken)" - {
        "[성공]: access/refresh 발급 후 refresh 해시를 redis에 저장하고 모델을 반환한다" {
            val userId = "user-1"

            every { generateTokenPort.generateAccessToken(userId) } returns "ACCESS"
            every { generateTokenPort.generateSecureRandom() } returns "REFRESH_RAW"
            every { generateTokenPort.hashSha256("REFRESH_RAW") } returns "REFRESH_HASH"

            every { authRedisPort.save(any(), any(), any()) } just Runs
            every { authRedisPort.addToSet(any(), any()) } just Runs

            val result = sut.issueToken(userId)

            result.accessToken() shouldBe "ACCESS"
            result.refreshToken() shouldBe "REFRESH_RAW"

            verify(exactly = 1) {
                authRedisPort.save(
                    "user-1:REFRESH_HASH",
                    "REFRESH_HASH",
                    Duration.ofDays(30)
                )
            }
            verify(exactly = 1) { authRedisPort.addToSet("user_tokens:user-1", "REFRESH_HASH") }
        }
    }

    "리프레시 토큰 발급(refreshLoginToken)" - {
        "[성공]: refreshToken이 존재하면 기존 토큰을 제거하고 새 토큰을 발급한다" {
            val userId = "user-1"
            val refreshRaw = "REFRESH_RAW"
            val refreshHash = "REFRESH_HASH"

            every { generateTokenPort.hashSha256(refreshRaw) } returns refreshHash
            every { authRedisPort.hasKey("$userId:$refreshHash") } returns true
            every { authRedisPort.delete("$userId:$refreshHash") } just Runs
            every { authRedisPort.removeFromSet("user_tokens:$userId", refreshHash) } just Runs

            every { generateTokenPort.generateAccessToken(userId) } returns "ACCESS2"
            every { generateTokenPort.generateSecureRandom() } returns "REFRESH_RAW2"
            every { generateTokenPort.hashSha256("REFRESH_RAW2") } returns "REFRESH_HASH2"
            every { authRedisPort.save("$userId:REFRESH_HASH2", "REFRESH_HASH2", Duration.ofDays(30)) } just Runs
            every { authRedisPort.addToSet("user_tokens:$userId", "REFRESH_HASH2") } just Runs

            val result = sut.refreshLoginToken(userId, refreshRaw)

            result.accessToken() shouldBe "ACCESS2"
            result.refreshToken() shouldBe "REFRESH_RAW2"

            verify(exactly = 1) { authRedisPort.hasKey("$userId:$refreshHash") }
            verify(exactly = 1) { authRedisPort.delete("$userId:$refreshHash") }
            verify(exactly = 1) { authRedisPort.removeFromSet("user_tokens:$userId", refreshHash) }
            verify(exactly = 1) { authRedisPort.save("$userId:REFRESH_HASH2", "REFRESH_HASH2", Duration.ofDays(30)) }
            verify(exactly = 1) { authRedisPort.addToSet("user_tokens:$userId", "REFRESH_HASH2") }
        }

        "[실패]: refreshToken이 redis에 없으면 AUTH_REFRESH_TOKEN_NOT_FOUND 예외를 던진다" {
            val userId = "user-1"
            val refreshRaw = "REFRESH_RAW"
            val refreshHash = "REFRESH_HASH"

            every { generateTokenPort.hashSha256(refreshRaw) } returns refreshHash
            every { authRedisPort.hasKey("$userId:$refreshHash") } returns false

            val ex = shouldThrow<AuthException> { sut.refreshLoginToken(userId, refreshRaw) }
            ex.errorCode shouldBe AUTH_REFRESH_TOKEN_NOT_FOUND

            verify(exactly = 0) { authRedisPort.delete(any()) }
            verify(exactly = 0) { authRedisPort.removeFromSet(any(), any()) }
        }
    }

    "토큰 제거(revokeToken)" - {
        "[성공]: refreshToken을 해싱해 redis key/value 및 user set에서 제거한다" {
            val userId = "user-1"
            val refreshRaw = "REFRESH_RAW"
            val refreshHash = "REFRESH_HASH"

            every { generateTokenPort.hashSha256(refreshRaw) } returns refreshHash
            every { authRedisPort.delete("$userId:$refreshHash") } just Runs
            every { authRedisPort.removeFromSet("user_tokens:$userId", refreshHash) } just Runs

            sut.revokeToken(userId, refreshRaw)

            verify(exactly = 1) { authRedisPort.delete("$userId:$refreshHash") }
            verify(exactly = 1) { authRedisPort.removeFromSet("user_tokens:$userId", refreshHash) }
        }
    }
})
