package br.com.bancoOriginal.grafo.repo;

import org.springframework.data.repository.CrudRepository;

import br.com.bancoOriginal.grafo.model.RouteDistance;

public interface RouteDistanceRepo extends CrudRepository<RouteDistance, Integer> {

	
	public void addRouteDistance(int weight, String source,String target);
}
