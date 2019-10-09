package com.testing.fuse.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.fuse.Dao.PruebaRowMapper;
import com.testing.fuse.pojo.Prueba;

public class PruebaService {
	private Prueba prueba;
	JdbcTemplate jdbcTemplate;

	private final String SQL_FIND_PERSON = "select * from people where id = ?";
	private final String SQL_DELETE_PERSON = "delete from people where id = ?";
	private final String SQL_UPDATE_PERSON = "update people set first_name = ?, last_name = ?, age  = ? where id = ?";
	private final String SQL_GET_ALL = "Select * FROM prueba";
	private final String SQL_INSERT_PERSON = "insert into people(id, first_name, last_name, age) values(?,?,?,?)";

	public PruebaService() {

	}

	@Autowired
	public PruebaService(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/*
	 * Description: Realiza la inserción en la base de datos 
	 * Author: JCorrea params:
	 * body { "idString": String, "nombre": String, "apellido": String, "direccion":
	 * String, "telefono": integer, "nacimiento": String } Return: String Sql
	 */
	public String insertDb(String pruebaJson) throws Exception {
		try {

			ObjectMapper objectMapper = new ObjectMapper();//llamamos a ObjectMapper de jakcson  
			prueba = objectMapper.readValue(pruebaJson, Prueba.class);// Convertimos json a object java
			String sql = "insert into prueba value (0,'" + prueba.getApellido() + "','" + prueba.getDireccion() + "','"
					+ prueba.getIdString() + "','" + prueba.getNacimiento() + "','" + prueba.getNombre() + "','"
					+ prueba.getTelefono() + "')";//Creamos sql
			System.out.println(sql);
			return sql; //retornamos query
		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * Description: Realiza la actualización en la base de datos 
	 * Author: JCorrea
	 * params: body { "idString": String, "nombre": String, "apellido": String,
	 * "direccion": String, "telefono": integer, "nacimiento": String } param: get
	 * id int Return: String Sql
	 */
	public String updateDb(String json, Exchange exchange) throws Exception {
		try {
			Map<String, Object> out = new HashMap<>(); //Creación de json de salida
			Integer id = exchange.getIn().getHeader("id", Integer.class);//Captura de parametro get id
			ObjectMapper objectMapper = new ObjectMapper(); //llamamos a ObjectMapper de jakcson  
			prueba = objectMapper.readValue(json, Prueba.class);// Convertimos json a object java
			String sql = "UPDATE prueba SET  apellido ='" + prueba.getApellido() + "'  WHERE id =" + id;
			System.out.println("salida sql" + sql); //Creamos sql
			out.put("message", "Se ha realizado la actualizacion correctamente.");//llenanos el json	
			exchange.getOut().setBody("hola mundo");//enviamos la salida json al body de camel
			return sql;//retornamos query
		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * Description: Realiza la actualización en la base de datos 
	 * Author: JCorrea
	 * param: id int return: String Sql
	 * param: Exchange manejo de mensajes con camel
	 */
	public String deleteDb(Exchange exchange) throws Exception {
		try {
			Integer id = exchange.getIn().getHeader("id", Integer.class);//Captura de parametro get id
			String sql = "DELETE FROM prueba WHERE id =" + id.toString(); //Creamos sql
			System.out.println("salida sql" + sql);
			return sql;//retornamos query
		} catch (Exception e) {
			throw e;

		}
	}

	/*
	 * Description: Realiza la actualización en la base de datos 
	 * Author: JCorrea
	 * return: String Sql
	 */
	public String selectDb() throws Exception {
		try {
//			List<Prueba> salida =  jdbcTemplate.query(SQL_GET_ALL, new PruebaRowMapper());
//			System.out.println("salida de valor:" + salida );
//			return salida;
			return "Select * FROM prueba";

		} catch (Exception e) {
			throw e;

		}
	}

}
