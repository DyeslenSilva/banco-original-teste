package br.com.bancoOriginal.tests;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.bancoOriginal.grafo.model.RouteDistance;
import br.com.bancoOriginal.grafo.model.RouteDistrict;
import br.com.bancoOriginal.service.RoutesService;

@SpringBootTest
public class DistanceRouteABC {

	@Mock
	@InjectMocks
	private RoutesService routeService;
	
	
	@Test
	public void testDistanceRouteABC(int maxStops,RouteDistance rdA,RouteDistance rdB, RouteDistrict rdC) {
		when(routeService.avaliableRoutes(maxStops, rdA, rdB).add(rdC)).then((Answer<?>) routeService.getRouteDistricts());
	}
	

}
