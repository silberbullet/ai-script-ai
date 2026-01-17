package club.auth.domain.type;

public enum UserStatus {
    ACTIVE,         // 활성화
    SUSPENDED,      // 일시 정지
    PROTECTED,      // 계정 보호 (주로 비밀번호를 연속으로 틀릴 때 등)
    REMOVED,        // 탈퇴
}
