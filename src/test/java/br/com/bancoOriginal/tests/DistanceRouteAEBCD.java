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
class DistanceRouteAEBCD {

	@Mock
	@InjectMocks
	private RoutesService routeService;
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

	void distanceRouteAEBCD(RouteDistrict district1, RouteDistrict district2) {
		when(routeService.minimiumDistanceDistrict(district1, district2)
				).then((Answer<?>) routeService.getRouteDistricts());
	}
}
