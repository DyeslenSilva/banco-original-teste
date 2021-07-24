/**
 * 
 */
package br.com.bancoOriginal.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.bancoOriginal.grafo.model.RouteDistance;
import br.com.bancoOriginal.grafo.model.RouteDistrict;
import br.com.bancoOriginal.service.RoutesService;
import junit.framework.Assert;

/**
 * @author Dyeslen
 *
 */
@SpringBootTest
class DistanceRouteAD {

	@Mock
	@InjectMocks
	private RoutesService routeService;
	
	
	
	void distanceRouteAD(RouteDistance rdA, RouteDistance rdD,int maxStops) {
		when(routeService.avaliableRoutes(maxStops, rdA, rdD)).thenReturn(routeService.getRouteDistricts());
	}
	
		
}
