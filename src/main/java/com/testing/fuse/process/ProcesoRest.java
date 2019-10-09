package com.testing.fuse.process;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.fuse.pojo.Comentarios;

public class ProcesoRest implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		String json = exchange.getIn().getBody(String.class);//Llamamos los datos de entrada en el body
		
		
		//Convertir a objetos
		ObjectMapper objectMapper = new ObjectMapper();//Llmaamos al metodo objeto de jackson
		Comentarios usuario = objectMapper.readValue(json, Comentarios.class);//Convertimos el json en un objeto java con jackson
		
		//invocamos la propiedad de la clase Usuario y cargamos el nuevo json
		Map<String, Object> jsonOut = new HashMap<>();
		System.out.print("salida de dato "+usuario.getEmail());//imprimimos para ver que ya son objetos
		
		//Construimos el json
		jsonOut.put("code", 100);
		jsonOut.put("titulo ",usuario.getName());
		jsonOut.put("email", usuario.getEmail());
			
		
		exchange.getOut().setBody(jsonOut);//ejecutamos la salida del json

	}

}


