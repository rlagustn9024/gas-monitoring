package com.elim.server.gas_monitoring.service;

import com.elim.server.gas_monitoring.config.SerialPortConfig;
import com.elim.server.gas_monitoring.dto.response.ua58kfg.UA58KFGHealthResponseDto;
import com.elim.server.gas_monitoring.dto.response.ua58kfg.UA58KFGMeasurementResponseDto;
import com.elim.server.gas_monitoring.exception.ErrorCode;
import com.elim.server.gas_monitoring.exception.exceptions.IntegrationException;
import com.fazecast.jSerialComm.SerialPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UA58KFGSensorService {



    /**
     * 센서 연결 상태 확인
     * */
    public UA58KFGHealthResponseDto checkStatus(String port) {
        SerialPort comPort = initPort(port); // 포트 관련 기본 설정

        if (!comPort.openPort()) { // 포트 연결 확인
            throw new IntegrationException(ErrorCode.PORT_OPEN_FAILED, "sensor.port.open.failed", List.of(port));
        }

        try {
            sendCommand(comPort, "ATCZ\r\n"); // 명령 전송

            int bytesRead = readResponse(comPort, new byte[1024]); // 응답 읽기

            if (bytesRead > 0) {
                return UA58KFGHealthResponseDto.of(true);
            } else {
                return UA58KFGHealthResponseDto.of(false);
            }
        } catch (Exception e) {
            throw new IntegrationException(ErrorCode.SENSOR_READ_FAILED, "sensor.read.failed");
        } finally {
            comPort.closePort();
        }
    }

    /* 포트 관련 기본 설정 */
    private SerialPort initPort(String port) {
        SerialPort comPort = SerialPort.getCommPort(port);
        comPort.setBaudRate(SerialPortConfig.BAUD_RATE);
        comPort.setNumDataBits(SerialPortConfig.DATA_BITS);
        comPort.setNumStopBits(SerialPortConfig.STOP_BITS);
        comPort.setParity(SerialPortConfig.PARITY);
        return comPort;
    }

    private void sendCommand(SerialPort comPort, String command) {
        comPort.writeBytes(command.getBytes(), command.length());
    }

    private int readResponse(SerialPort comPort, byte[] buffer) {
        return comPort.readBytes(buffer, buffer.length);
    }

    
    /**
     * 센서 측정값 조회
     * */
    public UA58KFGMeasurementResponseDto readValues(String port) {
        SerialPort comPort = initPort(port); // 포트 관련 기본 설정

        if (!comPort.openPort()) { // 포트 연결 확인
            throw new IntegrationException(ErrorCode.PORT_OPEN_FAILED, "sensor.port.open.failed", List.of(port));
        }

        try {
            sendCommand(comPort, "ATCQ\r\n"); // 명령 전송

            byte[] buffer = new byte[1024];
            int bytesRead = readResponse(comPort, buffer); // 응답 읽기

            if (bytesRead > 0) {
                String response = new String(buffer, 0, bytesRead);

                // "ATCQ " 접두사 제거
                if (response.startsWith("ATCQ")) {
                    response = response.substring(5).trim(); // "0,20.37,0,1390"
                }

                // CSV 파싱
                String[] parts = response.split(",");

                return UA58KFGMeasurementResponseDto.of(parts);
            } else {
                throw new IntegrationException(ErrorCode.SENSOR_READ_FAILED, "sensor.read.failed");
            }
        } finally {
            comPort.closePort();
        }
    }
}
