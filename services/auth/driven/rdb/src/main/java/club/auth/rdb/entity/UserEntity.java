package club.auth.rdb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import club.jpa.support.SnowflakeBaseTimeEntity;

@Entity
@Table(schema = "auth", name = "user")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends SnowflakeBaseTimeEntity {

    @Column(unique = true)
    public String username;

    @Column(unique = true)
    public String email;

    @Column(name = "encoded_password")
    public String encodedPassword;

    @Column(name = "login_retry_count")
    public Integer loginRetryCount = 0;

    @Column(name = "locked_until")
    public Instant lockedUntil;

    @Column(name = "status")
    @Convert(converter = UserEntityStatusConverter.class)
    public UserEntityStatus status;
}