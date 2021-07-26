package br.com.bancoOriginal.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.bancoOriginal.grafo.model.RouteDistance;
import br.com.bancoOriginal.service.RoutesService;

@SpringBootTest
class RouteDistanceCC {

	
	@Mock
	@InjectMocks
	private RoutesService routesService;
	
	void testRouteDistanceCC(int maxStop,RouteDistance routeDistance1, RouteDistance routeDistance2) {
		when(routesService.avaliableRoutes(maxStop, routeDistance1, routeDistance2)).then((Answer<?>) routesService.getRouteDistances());
		;
	}
	
	
	

}
