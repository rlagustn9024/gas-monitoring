package com.elim.server.gas_monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication // 이거 선언된 클래스가 스프링부트 애플리케이션의 시작점이 됨 -> 이 클래스 실행되면 컴포넌트 스캔 들어감(@ComponentScan이랑 거의 같음)
@EnableJpaAuditing // 엔티티가 생성, 수정될 때 자동으로 생성일시, 수정일시 등을 기록함
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO) // 이 어노테이션 쓰면 페이징 할 때 안정적인 JSON 구조 제공
@EnableScheduling // 스케쥴링 기능 활성화
@EnableAspectJAutoProxy // AOP 설정 활성화
public class GasMonitoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(GasMonitoringApplication.class, args);
	}

}
