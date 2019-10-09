package com.testing.fuse.bean;


import java.util.HashMap;
import java.util.Map;

public class respuestaSalida  {
	
	/*
	 * Descripcion: Imprime mensaje de salida insert.
	 * Author: JCORREA
	 */
	public Map<String, Object> insertOut() {	
		Map<String, Object> out = new HashMap<>();
		out.put("message", "Insercion correcta");		
		return out;
			
	}
	
	/*
	 * Descripcion: Imprime mensaje de salida update.
	 * Author: JCORREA
	*/
	public Map<String, Object> updateOut() {	
		Map<String, Object> out = new HashMap<>();
		out.put("message", "Actulizacion correcta");		
		return out;	
	}
	
	/*
	 * Descripcion: Imprime mensaje de salida delete.
	 * Author: JCORREA
	*/
	public Map<String, Object> deleteOut() {	
		Map<String, Object> out = new HashMap<>();
		out.put("message", "Se ha eliminado el registro correcta.");		
		return out;
		
	}

}
