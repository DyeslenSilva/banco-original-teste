package br.com.bancoOriginal.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.bancoOriginal.service.RoutesService;

@SpringBootTest
class DistanceRouteAtoC {

	@Mock
	@InjectMocks
	private RoutesService routesService;


}
