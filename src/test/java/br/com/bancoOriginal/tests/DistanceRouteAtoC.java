package br.com.bancoOriginal.tests;

import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.bancoOriginal.grafo.model.RouteDistance;
import br.com.bancoOriginal.service.RoutesService;

@SpringBootTest
class DistanceRouteAtoC {

	@Mock
	@InjectMocks
	private RoutesService routesService;

	
	void routeToABC(int maxRoutes,RouteDistance routeDistance1, RouteDistance routeDistance2) {
		when(routesService.avaliableRoutes(2, routeDistance1, routeDistance2)).then((Answer<?>) routesService.getRouteDistances());
	}
	
	void routeToADC(RouteDistance routeDistance1, RouteDistance routeDistance2) {
		when(routesService.avaliableRoutes(2, routeDistance1, routeDistance2)).then((Answer<?>) routesService.getRouteDistances());
	}

	
	void routeToAEBC() {
		
	}
	
	void routeToAEDBC(RouteDistance routeDistance1, RouteDistance routeDistance2) {
		when(routesService.avaliableRoutes(4, routeDistance1, routeDistance2)).then((Answer<?>) routesService.getRouteDistances());
	}
	
	
	
}
