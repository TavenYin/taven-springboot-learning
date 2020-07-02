package com.github.taven;

import com.github.taven.websocket.WebSocketSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@SpringBootApplication
@Controller
public class SpWebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpWebsocketApplication.class, args);
	}

	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

	@Autowired
	private WebSocketSupport webSocketSupport;

	@GetMapping("send")
	@ResponseBody
	public Object send(String id, String msg) {
		webSocketSupport.send(id, msg);
		return "OK";
	}

	@GetMapping("ws")
	public String ws() {
		return "ws";
	}

}
