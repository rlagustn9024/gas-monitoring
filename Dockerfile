# 1단계: 빌드용 이미지,
FROM gradle:8.4.0-jdk21 AS builder
WORKDIR /app

#
COPY build.gradle ./
COPY settings.gradle ./
COPY src ./src

# Gradle 캐시를 활용해 효율적 빌드
RUN gradle clean build -x test --no-daemon

# 2단계: 실행용 이미지, 최종 산출물(JAR 등)만 포함
# Debian 기반 이미지는 ubuntu랑 비슷하다고 생각하면 됨
# Oracle JDK가 원조이고 안정성도 높은데, 유료라서 Eclipse Temurin을 씀. 이것도 실무 표준으로 쓰는 JDK 중에 하나임
FROM eclipse-temurin:21-jre
WORKDIR /app

# 정확한 실행가능 JAR 파일만 복사
COPY --from=builder /app/build/libs/app.jar app.jar

EXPOSE 8081
EXPOSE 8443
ENTRYPOINT ["java", "-jar", "app.jar"]
