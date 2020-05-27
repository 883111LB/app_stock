package com.cvicse.stock;

import android.test.AndroidTestCase;
import android.util.Log;

import com.mitake.util.Compress;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.ExceptionHelper;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.internal.wire.MultiByteInteger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Created by panjiang on 18/4/13.
 */

public class SDKTest extends AndroidTestCase {

//    public void testV1804(){
//        TestSuite1804 suite1804 = new TestSuite1804();
//        suite1804.run(new TestResult());
//    }

    /**
     * 将16进制字符串转换为byte[]
     *
     * @param str
     * @return
     */
    public static byte[] toBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }

    public void testMqttBaowen() {
        String ss = "31940200093630303030302e736828b52ffd603300fd070036d33e2f8051c11fcc3c4fce3390786d33c7a2b2e5a56f29c162609d5a5091228b0911aff43d79ef534f6a6f4593e0b5a6b901310030002f00a8139ea891e73b7dcd926080da220c026a8b7848d4062111a0b6cd7ffa9a55fcdef73453be90a681341f30fb32def5d114cdf397cc553c51279ea8124f54e689aa3cbffbda266a538838a0360b8a06b5552212d436717fd3c72c510b58a2667806cef12eff7313cff76b60f9ec249e0f5de4f9cb3700c9673e0cc3427ff335abf61dbe8be7333e77b5cf479983a07502773179297fd7416a9fd7f5fe9e34a9dddd7d9eafe2f96b9d860781001d053836047c0018f0eb22cfef7516094a050685f27dd2f40100";
        byte[] bytes = toBytes(ss);
       in  = new DataInputStream(new ByteArrayInputStream(bytes));
        MqttWireMessage message = null;
        this.bais = new ByteArrayOutputStream();
        try {
            // read header
            if (remLen < 0) {
                // Assume we can read the whole header at once.
                // The header is very smallMoneyInflow so it's likely we
                // are able to read it fully or not at all.
                // This keeps the parser lean since we don't
                // need to cope with a partial header.
                // Should we lose synch with the stream,
                // the keepalive mechanism would kick in
                // closing the connection.
                bais.reset();

                byte first = 0;
                try {
                    first = in.readByte();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                byte type = (byte) ((first >>> 4) & 0x0F);
                if ((type < MqttWireMessage.MESSAGE_TYPE_CONNECT) ||
                        (type > MqttWireMessage.MESSAGE_TYPE_DISCONNECT)) {
                    // Invalid MQTT message type...
                    throw ExceptionHelper.createMqttException(MqttException.REASON_CODE_INVALID_MESSAGE);
                }
                remLen = readMBI(in).getValue();
                bais.write(first);
                // bit silly, we decode it then encode it
                bais.write(encodeMBI(remLen));
                packet = new byte[(int) (bais.size() + remLen)];
                packetLen = 0;
            }

            // read remaining packet
            if (remLen >= 0) {
                // the remaining packet can be read with timeouts
                readFully();

                // reset packet parsing state
                remLen = -1;

                byte[] header = bais.toByteArray();
                System.arraycopy(header, 0, packet, 0, header.length);
                message = MqttWireMessage.createWireMessage(packet);
                // @TRACE 501= received {0}
                String msg = "";
                if (message != null) {
                    byte decompress[] = Compress.getDecompressByByteArray(message.getPayload());
                    if (decompress != null) {
                        msg = new String(decompress);
                    }
                }
                Log.e("mqtt",msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    private ByteArrayOutputStream bais;
    private long remLen = -1;
    private long packetLen;
    private byte[] packet;
    DataInputStream in;

    protected static byte[] encodeMBI( long number) {
        int numBytes = 0;
        long no = number;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // Encode the remaining length fields in the four bytes
        do {
            byte digit = (byte)(no % 128);
            no = no / 128;
            if (no > 0) {
                digit |= 0x80;
            }
            bos.write(digit);
            numBytes++;
        } while ( (no > 0) && (numBytes<4) );

        return bos.toByteArray();
    }

    /**
     * Decodes an MQTT Multi-Byte Integer from the given stream.
     * @param in the input stream
     * @return {@link MultiByteInteger}
     * @throws IOException if an exception occurs when reading the input stream
     */
    protected static MultiByteInteger readMBI(DataInputStream in) throws IOException {
        byte digit;
        long msgLength = 0;
        int multiplier = 1;
        int count = 0;

        do {
            digit = in.readByte();
            count++;
            msgLength += ((digit & 0x7F) * multiplier);
            multiplier *= 128;
        } while ((digit & 0x80) != 0);

        return new MultiByteInteger(msgLength, count);
    }
    private void readFully() throws IOException {
        int off = bais.size() + (int) packetLen;
        int len = (int) (remLen - packetLen);
        if (len < 0)
            throw new IndexOutOfBoundsException();
        int n = 0;
        while (n < len) {
            int count = -1;
            try {
                count = in.read(packet, off + n, len - n);
            } catch (SocketTimeoutException e) {
                // remember the packet read so far
                packetLen += n;
                throw e;
            }

            if (count < 0)
                throw new EOFException();
            n += count;
        }
    }
    }
