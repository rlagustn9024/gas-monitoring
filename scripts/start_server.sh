#!/bin/bash

# 변수 설정
PROJECT_ROOT="/home/ec2-user/app"
APP_DIR="/home/ec2-user/app/build/libs"
LOG_DIR="/home/ec2-user/app/logs"

APP_FILE="$APP_DIR/one-0.0.1-SNAPSHOT.jar"

DEPLOY_LOG="$LOG_DIR/deploy.log"
ERROR_LOG="$LOG_DIR/error.log"
APP_LOG="$LOG_DIR/application.log"

TIME_NOW=$(date '+%Y-%m-%d %H:%M:%S')

# 환경변수 로드 (log 파일 못찾는 오류때문에 이거 넣음)
source ~/.bash_profile

# JAR 파일 실행
echo "$TIME_NOW > $APP_FILE 실행 시작." >> $DEPLOY_LOG
nohup java -jar "$APP_FILE" > "$APP_LOG" 2> "$ERROR_LOG" &
sleep 5

# 최대 5초 대기하면서 PID를 확인
for i in {1..5}; do
  CURRENT_PID=$(pgrep -f "$APP_FILE")
  if [ -n "$CURRENT_PID" ]; then
    echo "$TIME_NOW > 애플리케이션 실행 완료. 프로세스 ID: $CURRENT_PID" >> $DEPLOY_LOG
    break
  fi
  sleep 1
done

# 최종적으로 프로세스를 찾지 못하면 실패 처리
if [ -z "$CURRENT_PID" ]; then
  echo "$TIME_NOW > 애플리케이션 실행 실패. 에러 로그 확인: $ERROR_LOG" >> $DEPLOY_LOG
  exit 1
fi

echo "$TIME_NOW > 배포 스크립트 완료." >> $DEPLOY_LOG
