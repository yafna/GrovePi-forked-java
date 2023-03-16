package org.iot.raspberry.grovepi.pi4j;


import com.pi4j.io.i2c.I2C;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iot.raspberry.grovepi.GroveIO;

public class IO implements GroveIO {

    private final I2C device;

    public IO(I2C device) {
        this.device = device;
    }

    @Override
    public void write(int... command) throws IOException {
        byte[] buffer = new byte[command.length];
        for (int i = 0; i < command.length; i++) {
            buffer[i] = (byte) command[i];
        }
        Logger.getLogger("GrovePi").log(Level.INFO, "[Pi4J IO write]{0}", Arrays.toString(buffer));
        device.write(buffer, 0, command.length);
    }

    @Override
    public int read() throws IOException {
        final int read = device.read();
        Logger.getLogger("GrovePi").log(Level.INFO, "[Pi4J IO read]{0}", read);
        return read;
    }

    @Override
    public byte[] read(byte[] buffer) throws IOException {
        device.read(buffer, 0, buffer.length);
        Logger.getLogger("GrovePi").log(Level.INFO, "[Pi4J IO read]{0}", Arrays.toString(buffer));
        return buffer;
    }

}
