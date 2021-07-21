package br.com.bancoOriginal.grafo.model;

import java.util.ArrayList;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RouteDistrict implements Comparable<RouteDistrict>{

	//Vertice
	
	
	private String name;
	private int distance = 0;
	private RouteDistrict dad;
	private ArrayList<RouteDistance> incidents = new ArrayList<>();
	private ArrayList<RouteDistrict> neighbors = new ArrayList<>();
	private boolean visited = false;
	private String color = "white";
	

	
	public RouteDistrict(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(RouteDistrict vertex) {
		if(this.getDistance() < vertex.getDistance()) {
			return -1;
		}else if(this.getDistance() == vertex.getDistance()) {
			return 0;
		}
		return 1;
	}
	
	public void addIncidents(RouteDistance incident) {
		this.incidents.add(incident);
		
		if((incident.getSource().getName().equals(this.getName()))&&
				(!this.isNeighbors(incident.getTarget()))) {
			this.addNeighbors(incident.getTarget());
		}else if((incident.getTarget().getName().equals(this.getName()))&&
				(!this.isNeighbors(incident.getSource()))){
					this.addNeighbors(incident.getTarget());
				}
	}

	
	public void addNeighbors(RouteDistrict neighbors) {
		this.neighbors.add(neighbors);
	}
	
	public boolean isNeighbors(RouteDistrict neighbors) {
		for(int i =0; i < this.neighbors.size(); i++) 
			if(this.neighbors.get(i).getName().equals(neighbors.getName())) 
				return true;
			
				return false;
			}
	
	
	
	
	}
	

