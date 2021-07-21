package br.com.bancoOriginal.grafo.repo;

import org.springframework.data.repository.CrudRepository;

import br.com.bancoOriginal.grafo.model.RouteDistrict;

public interface RouteDistrictRepo extends CrudRepository<RouteDistrict, Integer>{
	
	public int addRouteDistrict(String name);
}
