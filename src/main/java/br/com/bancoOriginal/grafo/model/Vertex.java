package br.com.bancoOriginal.grafo.model;

import java.util.ArrayList;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Vertex implements Comparable<Vertex>{

	//Vertice
	
	
	private String name;
	private int distance = 0;
	private Vertex dad;
	private ArrayList<Edge> incidents = new ArrayList<>();
	private ArrayList<Vertex> neighbors = new ArrayList<>();
	private boolean visited = false;
	private String color = "white";
	
	
	@Override
	public int compareTo(Vertex vertex) {
		if(this.getDistance() < vertex.getDistance()) {
			return -1;
		}else if(this.getDistance() == vertex.getDistance()) {
			return 0;
		}
		return 1;
	}
	
	public void addIncidents(Edge incident) {
		this.incidents.add(incident);
		
		if((incident.getOrigin().getName().equals(this.getName()))&&
				(!this.isNeighbors(incident.getDestiny()))) {
			this.addNeighbors(incident.getDestiny());
		}else if((incident.getDestiny().getName().equals(this.getName()))&&
				(!this.isNeighbors(incident.getOrigin()))){
					this.addNeighbors(incident.getOrigin());
				}
	}

	
	public void addNeighbors(Vertex neighbors) {
		this.neighbors.add(neighbors);
	}
	
	public boolean isNeighbors(Vertex neighbors) {
		for(int i =0; i < this.neighbors.size(); i++) 
			if(this.neighbors.get(i).getName().equals(neighbors.getName())) 
				return true;
			
				return false;
			}
	
	
	
	
	}
	

