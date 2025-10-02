#!/bin/bash

# 변수 설정
PROJECT_ROOT="/home/ec2-user/app"
APP_DIR="/home/ec2-user/app/build/libs"
LOG_DIR="/home/ec2-user/app/logs"

APP_FILE="$APP_DIR/one-0.0.1-SNAPSHOT.jar"

DEPLOY_LOG="$LOG_DIR/deploy.log"

TIME_NOW=$(date '+%Y-%m-%d %H:%M:%S')

# 디렉토리가 없으면 생성
if [ ! -d "$LOG_DIR" ]; then
  echo "Directory $LOG_DIR does not exist. Creating it now."
  mkdir -p "$LOG_DIR"
fi

# 실행 중인 애플리케이션 종료
PID=$(pgrep -f $APP_FILE)
if [ -n "$PID" ]; then
  echo "$TIME_NOW > Stopping application with PID: $PID" >> $DEPLOY_LOG
  kill -15 $PID
  sleep 5
else
  echo "$TIME_NOW > No application running." >> $DEPLOY_LOG
fi
