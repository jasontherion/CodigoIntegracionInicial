package com.testing.fuse.process;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.testing.fuse.bean.CalculadoraImpl;



public class EndPointSoap implements Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		
        //Captura de parametros de entrada
		int param1 = exchange.getIn().getHeader("tipo",int.class);
		int param2 = exchange.getIn().getHeader("num1",int.class);
		int param3 = exchange.getIn().getHeader("num2",int.class);


		CalculadoraImpl suma = new CalculadoraImpl();

		int result = (int) suma.getCalculadora(param1,param2,param3);
		
		exchange.getOut().setBody(result);

	}

}
