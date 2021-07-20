package br.com.bancoOriginal.grafo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

//Aresta

@Data
@ToString
public class Edge {

	private int weight;
	private Vertex origin;
	private Vertex destiny;
	private boolean visited = false;
	
	
	public Edge (int weight, Vertex vertex, Vertex destiny) {
		this.setWeight(weight);
		this.setOrigin(destiny);
		this.setDestiny(destiny);
	}
	
	
	
}
