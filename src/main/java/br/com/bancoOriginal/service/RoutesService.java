package br.com.bancoOriginal.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

	@Getter @Setter
	private int distance;
	
	private List<Routes> listaRoutes = new ArrayList<Routes>();
	
	@Getter @Setter
	private boolean hasCycle = false;
	
	@Getter 
	private ArrayList<RouteDistance> routeDistances = new ArrayList<RouteDistance>();
	
	@Getter 
	private ArrayList<RouteDistrict> routeDistricts = new ArrayList<RouteDistrict>();
	
	private ArrayList<RouteDistrict> routesAvaliable = new ArrayList<RouteDistrict>();

	
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
			if(this.positionRouteDistrict(routeDistricts.get(i).getName())==
					this.routeDistricts.size()) {
				for(int j=0; j<routeDistricts.get(i).getIncidents().size(); j++) {
					if(routeDistricts.get(i).getName().equals(routeDistricts.get(i).getIncidents()
							.get(j).getSource().getName())&&(this.positionRouteDistrict(routeDistricts
									.get(i).getIncidents().get(j)
									.getTarget().getName())!=this.routeDistricts.size())) {
						
					this.addRouteDistance(routeDistricts.get(i).getIncidents().get(j).getWeight(),
							routeDistricts.get(i).getIncidents().get(j).getSource().getName(),
							routeDistricts.get(i).getIncidents().get(j).getTarget().getName());
					
					}else if((routeDistricts.get(i).getName().equals(routeDistricts.get(i).getIncidents().get(j).getTarget().getName()))&&
							(this.positionRouteDistrict(routeDistricts.get(i).getIncidents().get(j).getSource().getName())!=this.routeDistricts.size())) {
						
						this.addRouteDistance(routeDistricts.get(i).getIncidents().get(j).getWeight(),
								routeDistricts.get(i).getIncidents().get(j).getSource().getName(),
									routeDistricts.get(i).getIncidents().get(j).getTarget().getName());
					}
				}
				
				this.addRouteDistrict(routeDistricts.get(i).getName());
			}
		
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
	
	//Encontrar Vertice (Bairro especifíco)
	public RouteDistrict findRouteDistrict(String nome) {
		return this.routeDistricts.get(this.positionRouteDistrict(nome));
	}
	
	//Encontrar Aresta (Distancia entre bairros)
	public RouteDistance findRouteDistance(RouteDistrict routeDistance1,RouteDistrict routeDistance2) {
		for(int i=0; i<this.routeDistances.size(); i++) {
			if(((this.routeDistances.get(i).getSource().getName().equals(routeDistance1.getName()))&&
					(this.routeDistances.get(i).getTarget().getName().equals(routeDistance2.getName()))||
					((this.routeDistances.get(i).getSource().getName().equals(routeDistance2.getName()))&&
						this.routeDistances.get(i).getTarget().getName().equals(routeDistance1.getName())	))) {
				return this.routeDistances.get(i);
			}
		}
		return null;
	}
	
	
	
	
	
	//Retorna menor distancia entre as rotas
	public RouteDistance lowerWieght() {
		int j;
		for(j=0;j<this.getRouteDistances().size(); j++) {
			if((this.getRouteDistances().get(j).isVisited()==false)) {
				this.getRouteDistances().get(j).setVisited(true);
				
				for(int i=(j+1); i<this.getRouteDistances().size(); i++) {
					if((this.getRouteDistances().get(i).isVisited()==true)&&
							(this.getRouteDistances().get(j).getWeight() >
					this.getRouteDistances().get(i).getWeight())) {
						this.getRouteDistances().get(j).setVisited(false);
						j=i;
						this.getRouteDistances().get(j).setVisited(true);
					}
				}
				break;
			}
			
		}
		
		return this.getRouteDistances().get(j);	
	}
	
	//Encontra o caminho mais curto
	public ArrayList<RouteDistrict> leastCostlyWayDijkstra(RouteDistrict rd1, RouteDistrict rd2){
		
		ArrayList<RouteDistrict> leastWay = new ArrayList<RouteDistrict>();
		
		RouteDistrict rdWay;
		RouteDistrict rdActual;
		RouteDistrict neighbor;
		RouteDistance link;
		
		ArrayList<RouteDistrict> notVisited = new ArrayList<RouteDistrict>();
		
		leastWay.add(rd1);
		
		for(int i=0; i<this.getRouteDistricts().size(); i++) {
			if(this.getRouteDistricts().get(i).getName().equals(rd1.getName())) {
				
				this.getRouteDistricts().get(i).setDistance(0);
		
			}else {
				this.getRouteDistricts().get(i).setDistance(9999);
				notVisited.add(this.getRouteDistricts().get(i));
			}
		}
		
		Collections.sort(notVisited);
		
		while(!notVisited.isEmpty()) {
			rdActual= notVisited.get(0);
		
			for(int i=0; i<rdActual.getNeighbors().size(); i++) {
				neighbor = rdActual.getNeighbors().get(i);
				
				if(!neighbor.isVisited()) {
					link = this.findRouteDistance(rdActual, neighbor);
					if(neighbor.getDistance() > (rdActual.getDistance() + link.getWeight())) {
						neighbor.setDistance(rdActual.getDistance()+link.getWeight());
						neighbor.setDad(rdActual);
						
						if(neighbor==rd2) {
							leastWay.clear();
							rdWay = neighbor;
							leastWay.add(neighbor);
							while(rdWay.getDad() !=null) {
								leastWay.add(rdWay.getDad());
								rdWay = rdWay.getDad();
							}
							
							Collections.sort(leastWay);
						}
					}
				}
			}
			
			rdActual.setVisited(true);
			notVisited.remove(rdActual);
			
			
			Collections.sort(notVisited);
		}
		
		this.cleanRouteDistrictDad();
		return leastWay;
		
	}
	
	
	public boolean recursiveSearch(String source, String searching) {
		int posSource = this.positionRouteDistrict(source);
		
		this.routeDistricts.get(posSource).setVisited(true);
		
		if(!source.equals(searching)) {
			for(int i=0; i<this.routeDistricts.get(posSource).getNeighbors().size(); i++) {
				if(!this.routeDistricts.get(posSource).getNeighbors().get(i).isVisited()) {
					this.findRouteDistance(this.routeDistricts.get(posSource), 
							this.routeDistricts.get(posSource).getNeighbors().get(i)).setVisited(true);
					
					if(this.recursiveSearch(this.routeDistricts.get(posSource).getNeighbors().get(i).getName(),
							searching))
						return true;
					
				}
			}
		}else {
			return true;
		}
		return false;
	}
	
	public void markRouteDistrict(String routeDistrict) {
		int posSource = this.positionRouteDistrict(routeDistrict);
		
		this.routeDistricts.get(posSource).setVisited(true);
		
		for(int i=0; i<this.routeDistricts.get(posSource).getNeighbors().size(); i++) {
			if(!this.routeDistricts.get(posSource).getNeighbors().get(i).isVisited()) {
				this.markRouteDistrict(this.routeDistricts.get(posSource).getNeighbors().get(i).getName());
			}
		}
	}
	
	
	//Descobrir possiveis rotas
	public ArrayList<RouteDistrict> avaliableRoutes(int maxStops, RouteDistance rd1, RouteDistance rd2){
		
		for(int i=0; i<this.routesAvaliable.size(); i++) {
			if(maxStops==0) {
				System.out.println(routesAvaliable.get(i));
			}else {
				return routesAvaliable;
			}			
		}
		return routesAvaliable;
	}
	
	
	@SuppressWarnings("unused")
	public int minimiumDistanceDistrict(RouteDistrict rd1, RouteDistrict rd2) {
		for(int i=0;i<this.routeDistricts.size(); i++) {
			for(int j=0; j<this.routeDistricts.size(); j++) {
				if(routeDistricts.get(i).equals(routeDistricts.get(j))) {
					return 0;
				}else {
					return 1;
				}
			}
		}
		
		return routeDistricts.size();
	}
}
