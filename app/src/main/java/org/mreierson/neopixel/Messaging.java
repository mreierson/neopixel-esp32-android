package org.mreierson.neopixel;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

public class Messaging
{
    private static MqttClient mClient = null;
    private static MemoryPersistence mPersistence = new MemoryPersistence();

    public static final byte KEY_STRAND = 0x01;
    public static final byte KEY_PIXELS = 0x02;
    public static final byte KEY_FUNCTION = 0x03;
    public static final byte KEY_PARAMS = 0x04;

    public static final byte VALUE_FUNCTION_OFF = 0x00;
    public static final byte VALUE_FUNCTION_SOLID = 0x01;
    public static final byte VALUE_FUNCTION_SOLID2 = 0x02;
    public static final byte VALUE_FUNCTION_SOLID3 = 0x03;
    public static final byte VALUE_FUNCTION_CYLON = 0x04;
    public static final byte VALUE_FUNCTION_PULSE = 0x05;

    public static void sendOff(int strand, int pixels)
        throws Exception
    {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();

        packer.packMapHeader(4);

        packer.packByte(KEY_STRAND);
        packer.packByte((byte) strand);

        packer.packByte(KEY_PIXELS);
        packer.packShort((short) pixels);

        packer.packByte(KEY_FUNCTION);
        packer.packByte(VALUE_FUNCTION_OFF);

        packer.packByte(KEY_PARAMS);
        packer.packArrayHeader(0);

        sendMessage(packer.toByteArray());
    }

    public static void sendSolid(int strand, int pixels, int red, int green, int blue)
            throws Exception
    {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();

        packer.packMapHeader(4);

        packer.packByte(KEY_STRAND);
        packer.packByte((byte) strand);

        packer.packByte(KEY_PIXELS);
        packer.packShort((short) pixels);

        packer.packByte(KEY_FUNCTION);
        packer.packByte(VALUE_FUNCTION_SOLID);

        packer.packByte(KEY_PARAMS);

        packer.packArrayHeader(3);
        packer.packByte((byte) red);
        packer.packByte((byte) green);
        packer.packByte((byte) blue);

        sendMessage(packer.toByteArray());
    }

    public static void sendSolid2(int strand, int pixels, int red0, int green0, int blue0, int red1, int green1, int blue1, int delay)
            throws Exception
    {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();

        packer.packMapHeader(4);

        packer.packByte(KEY_STRAND);
        packer.packByte((byte) strand);

        packer.packByte(KEY_PIXELS);
        packer.packShort((short) pixels);

        packer.packByte(KEY_FUNCTION);
        packer.packByte(VALUE_FUNCTION_SOLID2);

        packer.packByte(KEY_PARAMS);

        packer.packArrayHeader(7);
        packer.packByte((byte) red0);
        packer.packByte((byte) green0);
        packer.packByte((byte) blue0);
        packer.packByte((byte) red1);
        packer.packByte((byte) green1);
        packer.packByte((byte) blue1);
        packer.packByte((byte) delay);

        sendMessage(packer.toByteArray());
    }

    public static void sendSolid3(int strand, int pixels, int red0, int green0, int blue0, int red1, int green1, int blue1, int red2, int green2, int blue2, int delay)
            throws Exception
    {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();

        packer.packMapHeader(4);

        packer.packByte(KEY_STRAND);
        packer.packByte((byte) strand);

        packer.packByte(KEY_PIXELS);
        packer.packShort((short) pixels);

        packer.packByte(KEY_FUNCTION);
        packer.packByte(VALUE_FUNCTION_SOLID3);

        packer.packByte(KEY_PARAMS);

        packer.packArrayHeader(10);
        packer.packByte((byte) red0);
        packer.packByte((byte) green0);
        packer.packByte((byte) blue0);
        packer.packByte((byte) red1);
        packer.packByte((byte) green1);
        packer.packByte((byte) blue1);
        packer.packByte((byte) red2);
        packer.packByte((byte) green2);
        packer.packByte((byte) blue2);
        packer.packByte((byte) delay);

        sendMessage(packer.toByteArray());
    }

    public static void sendPulse(int strand, int pixels, int red0, int green0, int blue0, int red1, int green1, int blue1, int steps, int delay)
            throws Exception
    {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();

        packer.packMapHeader(4);

        packer.packByte(KEY_STRAND);
        packer.packByte((byte) strand);

        packer.packByte(KEY_PIXELS);
        packer.packShort((short) pixels);

        packer.packByte(KEY_FUNCTION);
        packer.packByte(VALUE_FUNCTION_PULSE);

        packer.packByte(KEY_PARAMS);

        packer.packArrayHeader(8);
        packer.packByte((byte) red0);
        packer.packByte((byte) green0);
        packer.packByte((byte) blue0);
        packer.packByte((byte) red1);
        packer.packByte((byte) green1);
        packer.packByte((byte) blue1);
        packer.packByte((byte) steps);
        packer.packByte((byte) delay);

        sendMessage(packer.toByteArray());
    }


    public static void sendCylon(int strand, int pixels, int red, int green, int blue, int length, int delay)
            throws Exception
    {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();

        packer.packMapHeader(4);

        packer.packByte(KEY_STRAND);
        packer.packByte((byte) strand);

        packer.packByte(KEY_PIXELS);
        packer.packShort((short) pixels);

        packer.packByte(KEY_FUNCTION);
        packer.packByte(VALUE_FUNCTION_CYLON);

        packer.packByte(KEY_PARAMS);

        packer.packArrayHeader(5);
        packer.packByte((byte) red);
        packer.packByte((byte) green);
        packer.packByte((byte) blue);
        packer.packByte((byte) length);
        packer.packByte((byte) delay);

        sendMessage(packer.toByteArray());
    }

    public static void sendMessage(byte[] message)
            throws Exception
    {
        if ((mClient == null) || !mClient.isConnected()) {
            mClient = new MqttClient("tcp://192.168.200.20:1883", "NeoPixelAndroid", mPersistence);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            mClient.connect(options);
        }

        mClient.publish("neopixel", new MqttMessage(message));
    }
}
