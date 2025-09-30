package com.elim.server.gas_monitoring.domain.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // 이 엔티티에서 자동으로 생성, 수정일자를 기록함 (@CreatedDate, @LastModifiedDate)
// 스프링부트 설정에 @EnableJpaAuditing 추가해줘야 함 (여기서는 MainServerApplication에 추가해놨음)
@Getter
@SuperBuilder
public class BaseEntity extends BaseTimeEntity {

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;
}
