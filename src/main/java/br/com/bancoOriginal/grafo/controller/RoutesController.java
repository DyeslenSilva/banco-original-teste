package br.com.bancoOriginal.grafo.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.bancoOriginal.grafo.model.RouteDistance;
import br.com.bancoOriginal.grafo.model.RouteDistrict;
import br.com.bancoOriginal.service.RoutesService;

@RestController("/graph")
public class RoutesController {

	
	@Autowired
	private RoutesService routesService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/graph", method = RequestMethod.POST)
	public ResponseEntity<RoutesService> addRoute(@PathVariable RouteDistance routeDistance,
										@PathVariable RouteDistrict routeDistrict){
		
		routesService.addGraph(routeDistance, routeDistrict);
		return (ResponseEntity<RoutesService>) ResponseEntity.created(null);
	}
	
	@RequestMapping(path = "/graph/{graphID}", method = RequestMethod.GET)
	public ResponseEntity<RouteDistrict> returnRoute(@PathVariable String id) {
		RouteDistrict findDistrict =  routesService.findRouteDistrict(id);
		
		if(findDistrict.isVisited()) {
			return ResponseEntity.ok().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	@RequestMapping(path="/routes/from/{routeDistrict1}/to/{routeDistrict2}?maxStops={maxStops}")
	public ResponseEntity<RouteDistrict> avaliablesRoutes(RouteDistance routeDistrict1, RouteDistance routeDistrict2, int maxStops){
		
		ArrayList<RouteDistrict> avaliableRoutes = routesService.avaliableRoutes(maxStops, routeDistrict1, routeDistrict2);
		
		if(maxStops==0) {
			avaliableRoutes.clear();
		}else {
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.accepted().build();
	}
	
	
	
	
	
}
