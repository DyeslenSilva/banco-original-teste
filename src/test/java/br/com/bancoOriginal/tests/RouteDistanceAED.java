package br.com.bancoOriginal.tests;

import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.bancoOriginal.grafo.model.RouteDistrict;
import br.com.bancoOriginal.service.RoutesService;

@SpringBootTest
class RouteDistanceAED {

	
	@Mock
	@InjectMocks
	private RoutesService routeService;
	

	@org.junit.Test
	void routeDistaceAED(RouteDistrict routeDistrict1, RouteDistrict routeDistrict2) {
		when(routeService.findRouteDistance(routeDistrict1, routeDistrict2)).then((Answer<?>) routeService.getRouteDistricts());
	}

}
