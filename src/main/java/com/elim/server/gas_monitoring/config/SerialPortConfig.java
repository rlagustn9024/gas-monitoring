package com.elim.server.gas_monitoring.config;

import com.fazecast.jSerialComm.SerialPort;

public final class SerialPortConfig {

    public static final int BAUD_RATE = 19200;
    public static final int DATA_BITS = 8;
    public static final int STOP_BITS = SerialPort.ONE_STOP_BIT;
    public static final int PARITY = SerialPort.NO_PARITY;

    private SerialPortConfig() {}
}
