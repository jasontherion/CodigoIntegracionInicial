package com.testing.fuse.process;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ValidacionCola implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		 String indata =  exchange.getIn().getBody(String.class);
		 Map<String, Object> outData = new HashMap<>();
		 outData.put("message Out",indata);
		 System.out.println("salidaa de mensaje: "+indata);
		 exchange.getOut().setBody("salidaa de mensaje: "+indata);

	}
	
	
	public String mensajeEntradaCola() {
		
		return null;
	}

}
