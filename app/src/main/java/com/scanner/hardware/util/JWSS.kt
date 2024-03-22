package com.scanner.hardware.util

import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress

class JWSS(port: Int, val onChange: ((message: String) -> Unit) = {}) :
    WebSocketServer(InetSocketAddress(port)) {
    private val list = HashSet<WebSocket>()
    fun updateResult(message: String) {
        try {
            list.forEach {
                try {
                    it.send(message)
                } catch (_: Exception) {
                }
            }
        } catch (_: Exception) {
        }
    }

    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        list.add(conn)
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        list.remove(conn)
    }

    override fun onMessage(conn: WebSocket, message: String) = onChange(message)

    override fun onError(conn: WebSocket?, ex: Exception?) = Unit

    override fun onStart() {
        println("websocket start ws://0.0.0.0:${address.port}")
    }
}