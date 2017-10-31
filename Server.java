/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriaf.poo.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import seriaf.poo.server.config.ServerConfig;
import seriaf.poo.server.exceptions.InvalidFormatException;
import seriaf.poo.server.exceptions.MissingKeyException;
import seriaf.poo.server.exceptions.UnknownKeyException;
import seriaf.poo.structs.Message;
import seriaf.poo.structs.PrivateMessage;

/**
 *
 * @author professor
 */
public class Server {

    private final List<ServerPeer> mPeers;

    public Server() throws IOException, InvalidFormatException, UnknownKeyException, MissingKeyException {
        mPeers = new ArrayList<>();
    }
    
    //public Server()

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.listen();

        } catch (Throwable t) {
            System.err.println("Exception in thread main: " + t.getMessage());
        }
    }

    public synchronized void dispatch(Message message) {
        if (message instanceof PrivateMessage) {
            PrivateMessage privateMessage = (PrivateMessage) message;
            for (ServerPeer peer : mPeers) {
                if (privateMessage.getRecipient().equals(peer.getClientName())
                        || privateMessage.getSender().equals(peer.getClientName())) {
                    peer.sendMessage(message);
                }
            }
        } else {
            for (ServerPeer peer : mPeers) {
                peer.sendMessage(message);
            }
        }
    }

    public synchronized void cleanup(ServerPeer peer) {
        mPeers.remove(peer);
        dispatch(new Message("SERVER", peer.getClientName() + " has left the server."));
    }

    private void listen() throws IOException, InvalidFormatException, UnknownKeyException, MissingKeyException {
        ServerConfig config = new ServerConfig();
        ServerSocket serverSocket = new ServerSocket(config.getTcpPort());

        while (true) {
            Socket socket = serverSocket.accept();
            synchronized (this) {
                if (mPeers.size() >= config.getMaxClients()) {
                    socket.close();
                    continue;
                }

                ServerPeer peer = new ServerPeer(socket, this);
                dispatch(new Message("SERVER", "New client connected!"));
                mPeers.add(peer);
                peer.start();
            }
        }
    }
}
