package com.example;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MessageDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String msg;
	private Date enviado;
	private String origen;
	
	public MessageDTO() {}
	
	// Para la respuesta
	public MessageDTO(String msg, String origen) {
		this.msg = msg;
		this.origen = origen;
		enviado = new Date(System.currentTimeMillis());
	}

}
