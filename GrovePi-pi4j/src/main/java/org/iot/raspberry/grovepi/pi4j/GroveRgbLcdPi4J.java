package org.iot.raspberry.grovepi.pi4j;

import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfigBuilder;

import java.io.IOException;
import org.iot.raspberry.grovepi.GrovePiSequenceVoid;
import org.iot.raspberry.grovepi.devices.GroveRgbLcd;

public class GroveRgbLcdPi4J extends GroveRgbLcd {
    private final I2C rgb;
    private final I2C text;

    public GroveRgbLcdPi4J(Context pi4j) throws IOException {
        I2CConfigBuilder configrgb = (I2CConfigBuilder) I2C.newConfigBuilder(pi4j)
                .id("Grovepi-plus" + DISPLAY_RGB_ADDR)
                .name("My I2C Bus " + DISPLAY_RGB_ADDR)
                .bus(GrovePi4J.I2C_BUS)
                .device(DISPLAY_RGB_ADDR)
                .build();
        rgb = pi4j.create(configrgb);
        I2CConfigBuilder configtext = (I2CConfigBuilder) I2C.newConfigBuilder(pi4j)
                .id("Grovepi-plus" + DISPLAY_TEXT_ADDR)
                .name("My I2C Bus " + DISPLAY_TEXT_ADDR)
                .bus(GrovePi4J.I2C_BUS)
                .device(DISPLAY_TEXT_ADDR)
                .build();
        text = pi4j.create(configtext);
        init();
    }

    @Override
    public void close() {
        rgb.close();
        text.close();
    }

    @Override
    public void execRGB(GrovePiSequenceVoid sequence) throws IOException {
        synchronized (this) {
            sequence.execute(new IO(rgb));
        }
    }

    @Override
    public void execTEXT(GrovePiSequenceVoid sequence) throws IOException {
        synchronized (this) {
            sequence.execute(new IO(text));
        }
    }

}
