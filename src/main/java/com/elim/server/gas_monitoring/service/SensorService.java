package com.elim.server.gas_monitoring.service;

import com.elim.server.gas_monitoring.config.SerialPortConfig;
import com.elim.server.gas_monitoring.dto.response.health.HealthResponseDto;
import com.elim.server.gas_monitoring.dto.response.sensor.SensorPortResponseDto;
import com.elim.server.gas_monitoring.dto.response.sensor.ua58kfg.UA58KFGMeasurementResponseDto;
import com.elim.server.gas_monitoring.dto.response.sensor.ua58lel.UA58LELMeasurementResponseDto;
import com.elim.server.gas_monitoring.exception.ErrorCode;
import com.elim.server.gas_monitoring.exception.exceptions.IntegrationException;
import com.fazecast.jSerialComm.SerialPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SensorService {



    /**
     * 센서 연결 상태 확인
     * */
    public HealthResponseDto checkStatus(String port) {
        SerialPort comPort = initPort(port); // 포트 관련 기본 설정

        try {
            sendCommand(comPort, "ATCZ\r\n"); // 명령 전송
            int bytesRead = readResponse(comPort, new byte[1024]); // 응답 읽기

            if (bytesRead > 0) {
                return HealthResponseDto.of(true);
            } else {
                return HealthResponseDto.of(false);
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
        openPort(port, comPort); // 포트 연결
        return comPort;
    }

    /* 포트 연결 및 예외 처리 */
    private void openPort(String port, SerialPort comPort) {
        if (!comPort.openPort()) { // 포트 연결
            throw new IntegrationException(ErrorCode.PORT_OPEN_FAILED, "sensor.port.open.failed", List.of(port));
        }
    }

    private void sendCommand(SerialPort comPort, String command) {
        comPort.writeBytes(command.getBytes(), command.length());
    }

    private int readResponse(SerialPort comPort, byte[] buffer) {
        return comPort.readBytes(buffer, buffer.length);
    }

    
    /**
     * 센서 측정값 조회 (KFG)
     * */
    public UA58KFGMeasurementResponseDto readValuesFromKFG(String port) {
        SerialPort comPort = initPort(port); // 포트 연결 및 기본 설정

        try {
            sendCommand(comPort, "ATCQ\r\n"); // 명령 전송

            byte[] buffer = new byte[1024];
            int bytesRead = readResponse(comPort, buffer); // 응답 읽기

            if (bytesRead > 0) {
                String response = new String(buffer, 0, bytesRead);
                response = removeATCQPrefix(response); // 접두사 제거

                String[] parts = response.split(","); // CSV 파싱

                return UA58KFGMeasurementResponseDto.of(parts);
            } else {
                throw new IntegrationException(ErrorCode.SENSOR_READ_FAILED, "sensor.read.failed");
            }
        } finally {
            comPort.closePort();
        }
    }

    private String removeATCQPrefix(String response) {
        if (response.startsWith("ATCQ")) { // ATCQ 접두사 제거
            response = response.substring(5).trim(); // "0,20.37,0,1390"
        }
        return response;
    }


    /**
     * 센서 측정값 조회(LEL)
     * */
    public UA58LELMeasurementResponseDto readValuesFromLEL(String port) {
        SerialPort comPort = initPort(port); // 포트 연결 및 기본 설정

        try {
            sendCommand(comPort, "ATCQ\r\n"); // 명령 전송

            byte[] buffer = new byte[1024];
            int bytesRead = readResponse(comPort, buffer); // 응답 읽기

            if (bytesRead > 0) {
                String response = new String(buffer, 0, bytesRead);
                response = removeATCQPrefix(response); // 접두사 제거

                String[] parts = response.split(","); // CSV 파싱
                return UA58LELMeasurementResponseDto.of(parts);
            } else {
                throw new IntegrationException(ErrorCode.SENSOR_READ_FAILED, "sensor.read.failed");
            }
        } finally {
            comPort.closePort();
        }
    }

    
    /**
     * 전체 포트, 모델명 조회
     * */
    public SensorPortResponseDto getAllMappings() {
        SerialPort[] ports = SerialPort.getCommPorts(); // 전체 포트 조회

        List<SerialPort> usbPorts = extractUsbPorts(ports); // 전체 포트 중에 usb 포트만 추출

        List<String> portNames = extractUsbPortNames(usbPorts); // usb 포트 이름 추출

        Map<String , String> sensorMap = new LinkedHashMap<>(); // key: portName, value: Model

        for (String port : portNames) {
            SerialPort comPort = initPort(port); // 포트 연결 및 기본 설정

            try {
                sendCommand(comPort, "ATCVER\r\n"); // 명령 전송

                byte[] buffer = new byte[1024];
                int bytesRead = readResponse(comPort, buffer); // 응답 읽기

                if (bytesRead > 0) {
                    String response = new String(buffer, 0, bytesRead);
                    response = parseATCVERResponse(response); // 파싱
                    sensorMap.put(port, response);
                } else {
                    throw new IntegrationException(ErrorCode.SENSOR_READ_FAILED, "sensor.read.failed");
                }
            } finally {
                comPort.closePort();
            }
        }

        return SensorPortResponseDto.of(sensorMap);
    }

    private List<String> extractUsbPortNames(List<SerialPort> usbPorts) {
        return usbPorts.stream()
                .map(SerialPort::getSystemPortName)
                .toList();
    }

    private List<SerialPort> extractUsbPorts(SerialPort[] ports) {
        return Arrays.stream(ports)
                .filter(p -> p.getDescriptivePortName().toLowerCase().contains("usb"))
                .toList();
    }

    /* ATCVER UA58-KFG-U_7v3 -> UA58-KFG로 파싱 */
    private String parseATCVERResponse(String response) {
        if (response.startsWith("ATCVER")) { // ATCVER 접두사 제거
            response = response.substring(7).trim(); // UA58-LEL_0v7
        }

        // _ 이후 제거
        int underscoreIndex = response.indexOf('_');
        if (underscoreIndex > 0) {
            response = response.substring(0, underscoreIndex);
        }

        return response;
    }
}
