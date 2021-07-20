package br.com.bancoOriginal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.bancoOriginal.model.Routes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
public class RoutesService {

	private List<Routes> listaRoutes = new ArrayList<Routes>();
	
	@Getter @Setter
	private boolean hasCycle = false;
	
	
	public void clearList() {
		this.listaRoutes.clear();
		//hasCycle.setHasCycle(false);
		
		
	}
	
}
