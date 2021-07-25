package br.com.bancoOriginal.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.bancoOriginal.grafo.model.RouteDistance;
import br.com.bancoOriginal.grafo.model.RouteDistrict;
import br.com.bancoOriginal.service.RoutesService;

@SpringBootTest
class RouteDistanceADC {

	@Mock
	@InjectMocks
	private RoutesService routesService;
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

	
	@Test
	void testRouteDistanceADC(int maxStops, RouteDistance rdA, RouteDistrict rdC, RouteDistance rdD) {
		when(routesService.avaliableRoutes(maxStops, rdA, rdD).add(rdC)).then((Answer<?>) routesService.getRouteDistricts());
	}
}
