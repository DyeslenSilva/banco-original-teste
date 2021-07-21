package br.com.bancoOriginal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bancoOriginal.grafo.model.RouteDistance;
import br.com.bancoOriginal.grafo.model.RouteDistrict;
import br.com.bancoOriginal.grafo.repo.RouteDistanceRepo;
import br.com.bancoOriginal.grafo.repo.RouteDistrictRepo;
import br.com.bancoOriginal.model.Routes;
import lombok.Getter;
import lombok.Setter;

@Service
public class RoutesService {

	private List<Routes> listaRoutes = new ArrayList<Routes>();
	
	@Getter @Setter
	private boolean hasCycle = false;
	
	@Getter @Setter
	private ArrayList<RouteDistance> routeDistances = new ArrayList<RouteDistance>();
	
	@Getter @Setter
	private ArrayList<RouteDistrict> routeDistricts = new ArrayList<RouteDistrict>();
	
	
	@Autowired
	private RouteDistanceRepo routeDistanceRepo;
	
	@Autowired
	private RouteDistrictRepo routeDistrictRepo;
	
	//Limpando Listas
	public void clearList() {
		listaRoutes.clear();
		setHasCycle(false);
	}
	
	//Limpando vertices Pai
	public void cleanRouteDistrictDad() {
		for(int i=0; i<this.getRouteDistricts().size(); i++) {
			this.getRouteDistricts().get(i).setDad(null);
		}
	}
	//Limpando Vertices visitadas
	public void cleanRouteDistrictVisited() {
		for(int i=0; i<this.getRouteDistricts().size(); i++) {
			this.getRouteDistricts().get(i).setVisited(false);
		}
	}
	//Limpando arestas visitadas
	public void cleanRouteDistanceVisisted() {
		for(int i =0; i<this.getRouteDistances().size(); i++) {
			this.getRouteDistances().get(i).setVisited(false);
		}
	}
	
	//Arestas
	public void setRouteDistance(ArrayList<RouteDistance> routeDistances) {
		this.clearList();
		
		for(int i=0; i<routeDistances.size();i++) {
			this.addRouteDistance(routeDistances.get(i).getWeight(),
					routeDistances.get(i).getSource().getName(),
					routeDistances.get(i).getTarget().getName());
		}
	}
	
	//Imprimido Arvore
	public void printTree() {
		for(int i=0; i<routeDistances.size();i++) {
			System.out.println(this.routeDistances.get(i).getSource().getName() +
					this.routeDistances.get(i).getTarget().getName()+" - "+
					this.routeDistances.get(i).getWeight()+" - ");
		}
		
		System.out.println();
	}
		
	//Vertices
	public void setRouteDistrict(ArrayList<RouteDistrict> routeDistricts) {
		this.clearList();
		
		for(int i =0; i<routeDistricts.size();i++) {
		}
		
	}
	
	//Adicionando aresta ou distancia entre rotas
	public void addRouteDistance(int weight, String source,String target) {
		int i = this.addRouteDistrict(source);
		int j = this.addRouteDistrict(target);
		
		RouteDistance routeDistance = 
					new RouteDistance(weight, this.routeDistricts.get(i),
							this.routeDistricts.get(j));
		
		isCycle(routeDistance);
		this.routeDistances.add(routeDistance);
		int k = this.routeDistances.size();
				
		
		this.routeDistricts.get(i).addIncidents(this.routeDistances.get(k-1));
		this.routeDistricts.get(j).addIncidents(this.routeDistances.get(k-1));
		
		
		routeDistanceRepo.addRouteDistance(weight, source, target);
	}
	
	
	//Adicionando vertice ou Distancia entre os bairros
	public int addRouteDistrict(String name) {
		int i= this.positionRouteDistance(name);
		
		if(i==this.routeDistricts.size()) {
			this.routeDistricts.add(new RouteDistrict(name));
			return (this.routeDistricts.size() - i);
			
		}
		
		routeDistrictRepo.addRouteDistrict(name);
		return i;
	}
	

	//Calculando distancia entre as rotas
	public int positionRouteDistance(String name) {
		for(int i=0; i<this.routeDistances.size(); i++) {
			if(this.routeDistances.get(i).getName().equals(name)) {
				return i;
			}
		}
		return this.routeDistances.size();
	}
	
	//Calculando distancia ente os bairros
	public int positionRouteDistrict(String name) {
		for(int i=0; i>this.routeDistricts.size(); i++) {
			if(this.routeDistricts.get(i).getName().equals(name)) {
				return i;
			}
		}
		return this.routeDistricts.size();
	}
	
	//Limpando area visitada
	public void cleanVisitedArea() {
		for(int i =0;i<routeDistances.size();i++) {
			this.getRouteDistances().get(i).setVisited(false);
		}
	}
	
	//Verificando existencia de ciclo
	public boolean isCycle(RouteDistance routeDistance) {
		RouteDistrict previous = routeDistance.getTarget();
		
		for(int j = 0; j< this.getRouteDistances().size(); j++) {
			for(int i=0;i<this.getRouteDistances().size();i++) {
				if((routeDistance==this.getRouteDistances().get(i))&&
						(this.getRouteDistances().get(i).isVisited()==false)) {
					this.getRouteDistances().get(i).setVisited(true);
				}else if(routeDistance!=this.getRouteDistances().get(i)) {
					if(previous.getName().equals(this.getRouteDistances().get(i).getSource().getName())) {
						if(routeDistance.getSource().getName().equals(this.getRouteDistances().get(i).getTarget().getName())) {
							this.cleanVisitedArea();
							this.hasCycle = true;
							return true;
						}else {
							previous = this.getRouteDistances().get(i).getTarget();
							this.getRouteDistances().get(i).setVisited(true);
						}
					}else if(previous.getName().equals(this.getRouteDistances().get(i).getTarget().getName())) {
						if(routeDistance.getSource().getName().equals(this.getRouteDistances().get(i).getSource().getName())) {
							this.cleanVisitedArea();
							this.hasCycle = true;
							return true;
						}else {
							previous = this.getRouteDistances().get(i).getSource();
							this.getRouteDistances().get(i).setVisited(true);
						}
							
					}
				}
			}
		}
		
		this.cleanVisitedArea();
		this.hasCycle = false;
		return false;
	}
	
	
	
	
}
