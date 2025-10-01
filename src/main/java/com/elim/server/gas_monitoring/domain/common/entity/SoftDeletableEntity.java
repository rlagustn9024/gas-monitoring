package com.elim.server.gas_monitoring.domain.common.entity;

import com.elim.server.gas_monitoring.domain.enums.common.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Getter
@Audited
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SoftDeletableEntity extends BaseTimeEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    protected Status status = Status.ACTIVE;

    protected LocalDateTime deletedAt;

    public void softDelete() {
        this.status = Status.DELETED;
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return this.status == Status.DELETED;
    }
}
