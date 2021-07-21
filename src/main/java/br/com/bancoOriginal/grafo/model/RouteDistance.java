package br.com.bancoOriginal.grafo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

//Aresta

@Data
@ToString
public class RouteDistance {

	private int weight;
	private String name;
	private RouteDistrict source;
	private RouteDistrict target;
	private boolean visited = false;


	public RouteDistance(int weight2, RouteDistrict routeDistrict, RouteDistrict routeDistrict2) {
		// TODO Auto-generated constructor stub
	}
	
	
	
}
