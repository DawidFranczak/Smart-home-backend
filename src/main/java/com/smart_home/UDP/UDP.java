package com.smart_home.UDP;

import com.smart_home.Exception.NotFound404Exception;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

@Service
public class UDP {
    /**
     *
     * @param messageToSend A message to be sent to the microcontroller.
     * @param port Microcontroller's port.
     * @param ip Microcontroller's ip
     * @param timeout Time to wait for microcontroller response
     * @return Diagram Packet entity with microcontroller response.
     * @throws IOException
     */
    public DatagramPacket send(String messageToSend, int port, InetAddress ip, int timeout) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(timeout);
        byte[] sendData = messageToSend.getBytes();
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
        socket.send(sendPacket);
        socket.receive(receivePacket);
        socket.close();
        return receivePacket;
    }

    /**
     *
     * @param message A message to be sent to the microcontroller.
     * @param port Microcontroller's port.
     * @param timeoutInMs Time to wait for microcontroller response
     * @param exceptionMessage Message to be displayed in case of communication error.
     * @return Diagram Packet entity with microcontroller response.
     */
    public DatagramPacket scanLocalHost(String message,int port,int timeoutInMs,String exceptionMessage) {
        for (int i = 2; i < 254; i++) {
            try {
                return send(message, port, InetAddress.getByName("192.168.0." + i), timeoutInMs);
            } catch (IOException e) {
                continue;
            }
        }
        throw new NotFound404Exception(exceptionMessage);
    }
}
