package com.testing.fuse.bean;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebService(serviceName = "Calculadora2", targetNamespace ="http://com.jasonTherion/")
public class CalculadoraImpl  {

private static final Logger LOG = LoggerFactory.getLogger(CalculadoraImpl.class);
@WebMethod
@WebResult(name = "resultado" )
public double getCalculadora(@WebParam(name = "tipo") int tipo, @WebParam(name = "num1") int num1, @WebParam(name = "num2") int num2) {

		double resultado = 0.0;

		switch (tipo) {
		case 1:
			System.out.println("suma ok");
			resultado = num1 + num2;
			break;
		case 2:
			System.out.println("resta ok");
			resultado = num1 - num2;
			break;
		case 3:
			System.out.println("multiplicacion ok");
			resultado = num1 * num2;
			break;
		default:
			break;
		}

		return resultado;
	}

}   
