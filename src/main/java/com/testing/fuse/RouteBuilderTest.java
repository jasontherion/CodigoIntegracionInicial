package com.testing.fuse;


import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;

import com.testing.fuse.bean.PruebaService;
import com.testing.fuse.bean.respuestaSalida;
import com.testing.fuse.process.EndPointSoap;
import com.testing.fuse.process.ProcesoRest;
import com.testing.fuse.process.ValidacionCola;

public class RouteBuilderTest extends RouteBuilder {
	private JacksonDataFormat jsonOut = new JacksonDataFormat(String.class);
	
	@Override
	public void configure() throws Exception {
		/******************************************** Codigo hola mundo    *****************************************************/		
   
	restConfiguration()  // 1. Creación de configuración rest
        .component("jetty") // 2. Implementacion de componente jetty como servidor
        .port("9104") // 3. Puerto de implementación de los Endpoints creados 
        .contextPath("/api"); // 4. Contexto de los endPoints.

 
	 rest("/crm")//5. Implementacion rest, el “/crm” es parte url 
	   .get("test") //6. Método de consulta rest, el “test” es parte url
         .to("direct:Hola"); //7. Invocación de inicio de proceso  
	 
     from("direct:Hola") //8. Inicio de proceso 
          .setHeader(Exchange.CONTENT_TYPE, simple("application/json"))//9.   Salida esperada del endpoint
	      .transform()//10. Función de transformación de salida
          .constant("Hola mundo");//11. Cosntrucción de salida de body para el endPoint en formato texto
		

	
	
/********************************************  Codigo leer rest    *****************************************************/
	
	    rest("ws_rest").consumes("application/json")
	    .get("json")
	    .to("direct:post");
	    
	    
	      from("direct:post")
	      .setHeader(Exchange.HTTP_METHOD,constant("GET"))
	      .setHeader(Exchange.CONTENT_TYPE, simple("application/json"))
	      .to("http4://jsonplaceholder.typicode.com/comments/1?bridgeEndpoint=true&throwExceptionOnFailure=false")
	      .process(new ProcesoRest())  
	      .marshal(jsonOut)
	      .end();
	      
/********************************************  Codigo Crear Soap    *****************************************************/      
	      
	      
	       from("cxf:bean:cxfSoapServiceEndpoint3")
	         .setHeader("tipo",simple("${body[0]}"))
	         .setHeader("num1",simple("${body[1]}"))
	         .setHeader("num2",simple("${body[2]}"))
	         .process(new EndPointSoap())
		 .end();
	       
/********************************************  Codigo Consumir Soap    *****************************************************/      
		             
	       from("cxf:bean:cxfSoapServiceEndpoint")
			 .process(new EndPointSoap())
		   .end();
	       
	       
/********************************************  Codigo crud db mysql   *****************************************************/      
		    
	   	rest("rest")
        .consumes("application/json")
        .post("jason")
        .to("direct:dbInsert");

	    rest("rest")
         .put("jason/{id}").to("direct:dbUpdate");

	    rest("rest")
          .delete("jason/{id}").to("direct:dbDelete");

        rest("rest")
          .consumes("application/json")
          .get("jason").to("direct:dbSelect");

	       
	       
	       from("direct:dbInsert")
	 		    .to("log:?level=INFO&showBody=true")
	 		    .setHeader(Exchange.HTTP_METHOD,constant("POST"))
	            .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("201"))
	            .setHeader(Exchange.CONTENT_TYPE, simple("application/json"))
	            .bean(new PruebaService(),"insertDb")
	 		    .to("jdbc:dataSource")
	 		    .to("log:?level=INFO&showBody=true")
		 	    .bean(new respuestaSalida(), "insertOut")
	            .to("log:?level=INFO&showBody=true")
	 		    .end();

		 	from("direct:dbUpdate")
		 	     .to("log:?level=INFO&showBody=true")
		 	     .setHeader(Exchange.HTTP_METHOD,constant("PUT"))
		 	     .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("200"))
		 	     .setHeader(Exchange.CONTENT_TYPE,   simple("application/json"))
		         .bean(new PruebaService(),"updateDb")
		 	     .to("jdbc:dataSource")
//		 	     .to("activemq:queue:legis?exchangePattern=InOnly&transferExchange=true")
			 	 .bean(new respuestaSalida(), "updateOut")
		 	     .marshal(jsonOut)
		 	     .end();
	
		     from("direct:dbDelete").to("log:?level=INFO&showBody=true")
		 	    .setHeader(Exchange.HTTP_METHOD,constant("DELETE"))
		 	    .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("200"))
		        .bean(new PruebaService(),"deleteDb")
		 	    .to("jdbc:dataSource")
		 	    .to("log:?level=INFO&showBody=true")
			 	.bean(new respuestaSalida(), "deleteOut")
		 	    .marshal(jsonOut)
		 	    .end();
	
		    from("direct:dbSelect").to("log:?level=INFO&showBody=true")
		        .setHeader(Exchange.HTTP_METHOD,constant("GET"))
		 	    .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("200"))
		 	    .setHeader(Exchange.CONTENT_TYPE, simple("application/json"))
		        .bean(new PruebaService(), "selectDb")
		 	    .to("jdbc:dataSource")
		 	    .to("log:?level=INFO&showBody=true")
		 	    .marshal(jsonOut)
		 	    .end();
		    
/********************************************  Codigo de creacion y consumo de colas   *****************************************************/ 	    
		    
			rest("cola")
			.get("solicitud/{message}")
			.to("direct:enviarMensajeCola");
			
			from("activemq:queue:testCola1?exchangePattern=InOnly&transferExchange=true")
//			    .setBody().simple("Esto es el mesaje enviado 2 ${date:now:yyyy-MM-dd HH:mm:ss}")
//			    .to("activemq:queue:legis?exchangePattern=InOnly&transferExchange=true")
//			    .setHeader("Exchange.HTTP_RESPONSE_CODE")
			    .process(new ValidacionCola())		    
			    .log("${body}")
			    .end();

		
	     from("direct:enviarMensajeCola")
	         .setBody().simple("${header.message} ${date:now:yyyy-MM-dd HH:mm:ss}")
			.to("activemq:queue:testCola1?exchangePattern=InOnly&transferExchange=true")
//		    .process(new ValidacionCola())
//		    .marshal(jsonOut)
			.end();	

	
	}
	



}
