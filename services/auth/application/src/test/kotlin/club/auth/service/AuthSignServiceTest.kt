package club.auth.service

import club.auth.domain.User
import club.auth.domain.type.UserStatus
import club.auth.exception.AuthErrorCode.AUTH_ACCOUNT_ALREADY_EXIST
import club.auth.exception.AuthErrorCode.AUTH_ACCOUNT_LOGIN_FAILED
import club.auth.exception.AuthException
import club.auth.port.AuthCommandRepositoryPort
import club.auth.port.PasswordEncoderPort
import club.auth.readmodel.AuthCommandModels.*
import club.auth.usecase.token.AuthIssueTokenUseCase
import club.auth.usecase.token.AuthRevokeTokenUseCase
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import java.util.*
import java.util.function.Supplier

class AuthSignServiceTest : FreeSpec({

    val authIssueTokenUseCase = mockk<AuthIssueTokenUseCase>()
    val authRevokeTokenUseCase = mockk<AuthRevokeTokenUseCase>()
    val commandRepository = mockk<AuthCommandRepositoryPort>()
    val passwordEncoder = mockk<PasswordEncoderPort>()

    val sut = AuthSignService(
        authIssueTokenUseCase,
        authRevokeTokenUseCase,
        commandRepository,
        passwordEncoder
    )

    beforeTest { clearAllMocks() }

    "이메일 중복 확인(existsByEmail)" - {
        "[성공]: 이메일 존재 여부를 repository에 위임한다" {
            every { commandRepository.existsByEmail("a@test.com") } returns true

            sut.existsByEmail("a@test.com") shouldBe true

            verify(exactly = 1) { commandRepository.existsByEmail("a@test.com") }
        }
    }

    "회원가입(signUp)" - {
        "[성공]: 중복이 없으면 사용자 저장 후 토큰을 발급한다" {
            val req = SignUpRequestModel("user1", "user1@test.com", "pw1234")

            every { commandRepository.existsByEmail(req.email()) } returns false
            every { passwordEncoder.encode(req.password()) } returns "ENC_PW"

            val savedUser = User.builder()
                .username(req.username())
                .email(req.email())
                .encodedPassword("ENC_PW")
                .status(UserStatus.ACTIVE)
                .build()

            every { commandRepository.save(any()) } returns savedUser
            every { commandRepository.transaction(any<Supplier<User>>()) } answers { firstArg<Supplier<User>>().get() }

            val token = mockk<LoginTokenModel>()
            every { authIssueTokenUseCase.issueToken(savedUser.id) } returns token

            val result = sut.signUp(req)

            result shouldBe token

            verify(exactly = 1) { commandRepository.transaction(any<Supplier<User>>()) }
            verify(exactly = 1) { commandRepository.existsByEmail(req.email()) }
            verify(exactly = 1) { passwordEncoder.encode(req.password()) }
            verify(exactly = 1) {
                commandRepository.save(match {
                    it.email == req.email() &&
                            it.username == req.username() &&
                            it.encodedPassword == "ENC_PW" &&
                            it.status == UserStatus.ACTIVE
                })
            }
            verify(exactly = 1) { authIssueTokenUseCase.issueToken(savedUser.id) }
        }

        "[실패]: 이메일이 이미 존재하면 AUTH_ACCOUNT_ALREADY_EXIST 예외를 던진다" {
            val req = SignUpRequestModel("user1", "user1@test.com", "pw1234")

            every { commandRepository.existsByEmail(req.email()) } returns true
            every { commandRepository.transaction(any<Supplier<User>>()) } answers { firstArg<Supplier<User>>().get() }

            val ex = shouldThrow<AuthException> { sut.signUp(req) }
            ex.errorCode shouldBe AUTH_ACCOUNT_ALREADY_EXIST

            verify(exactly = 0) { passwordEncoder.encode(any()) }
            verify(exactly = 0) { commandRepository.save(any()) }
            verify(exactly = 0) { authIssueTokenUseCase.issueToken(any()) }
        }
    }

    "로그인(signIn)" - {
        "[성공]: 이메일이 존재하고 비밀번호가 일치하면 토큰을 발급한다" {
            val req = SignInRequestModel("user1@test.com", "pw1234")

            val user = User.builder()
                .username("user1")
                .email(req.email())
                .encodedPassword("ENC_PW")
                .status(UserStatus.ACTIVE)
                .build()

            every { commandRepository.findByEmail(req.email()) } returns Optional.of(user)
            every { passwordEncoder.matches(req.password(), "ENC_PW") } returns true

            val token = mockk<LoginTokenModel>()
            every { authIssueTokenUseCase.issueToken(user.id) } returns token

            val result = sut.signIn(req)

            result shouldBe token

            verify(exactly = 1) { commandRepository.findByEmail(req.email()) }
            verify(exactly = 1) { passwordEncoder.matches(req.password(), "ENC_PW") }
            verify(exactly = 1) { authIssueTokenUseCase.issueToken(user.id) }
        }

        "[실패]: 이메일이 없으면 AUTH_ACCOUNT_LOGIN_FAILED 예외를 던진다" {
            val req = SignInRequestModel("missing@test.com", "pw1234")
            every { commandRepository.findByEmail(req.email()) } returns Optional.empty()

            val ex = shouldThrow<AuthException> { sut.signIn(req) }
            ex.errorCode shouldBe AUTH_ACCOUNT_LOGIN_FAILED

            verify(exactly = 0) { passwordEncoder.matches(any(), any()) }
            verify(exactly = 0) { authIssueTokenUseCase.issueToken(any()) }
        }

        "[실패]: 비밀번호가 불일치하면 AUTH_ACCOUNT_LOGIN_FAILED 예외를 던진다" {
            val req = SignInRequestModel("user1@test.com", "wrong")

            val user = User.builder()
                .username("user1")
                .email(req.email())
                .encodedPassword("ENC_PW")
                .status(UserStatus.ACTIVE)
                .build()

            every { commandRepository.findByEmail(req.email()) } returns Optional.of(user)
            every { passwordEncoder.matches(req.password(), "ENC_PW") } returns false

            val ex = shouldThrow<AuthException> { sut.signIn(req) }
            ex.errorCode shouldBe AUTH_ACCOUNT_LOGIN_FAILED

            verify(exactly = 0) { authIssueTokenUseCase.issueToken(any()) }
        }
    }

    "로그아웃(logout)" - {
        "[성공]: 로그아웃 요청 시 revokeToken을 호출한다" {
            val req = SignOutRequestModel("user-1", "refresh-raw")

            every { authRevokeTokenUseCase.revokeToken(req.userId(), req.refreshToken()) } just Runs

            sut.logout(req)

            verify(exactly = 1) { authRevokeTokenUseCase.revokeToken("user-1", "refresh-raw") }
        }
    }
})
