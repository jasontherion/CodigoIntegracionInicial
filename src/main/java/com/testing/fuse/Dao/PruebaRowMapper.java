package com.testing.fuse.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.testing.fuse.pojo.Prueba;

public class PruebaRowMapper implements RowMapper<Prueba>  {

	@Override
	public Prueba mapRow(ResultSet rs, int rowNum) throws SQLException {

		Prueba prueba = new Prueba();
        prueba.setId(rs.getInt("id"));
        prueba.setIdString(rs.getString("idString"));
        prueba.setNombre(rs.getString("nombre"));
        prueba.setApellido(rs.getString("apellido"));
        prueba.setTelefono(rs.getInt("telefono"));
        prueba.setDireccion(rs.getString("direccion"));
        prueba.setNacimiento(rs.getString("nacimiento"));         
		return prueba;
	}



}
