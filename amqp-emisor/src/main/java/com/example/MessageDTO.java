package com.example;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;

@Data
public class MessageDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String msg;
	private String origen;
	private Date enviado = new Date();
	
	public MessageDTO() { }
	
	public MessageDTO(String msg, String origen) {
		this.msg = msg;
		this.origen = origen;
	}
}
