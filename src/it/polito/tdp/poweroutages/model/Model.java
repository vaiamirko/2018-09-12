package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.poweroutages.db.PowerOutagesDAO;

public class Model {
	
	Graph<Nerc, DefaultWeightedEdge> grafo ;
	PowerOutagesDAO dao = new PowerOutagesDAO();
	Map<Integer,Nerc> mappaNerc ;
	List<Vicino> vicini;
	
	public void CreaGrafo(Nerc scelto)
	{
		mappaNerc = new HashMap<>();
		grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		vicini = new ArrayList<>();
		
		Graphs.addAllVertices(grafo,dao.loadAllNercs() );
		
		for(Nerc n : grafo.vertexSet()) {
			mappaNerc.put(n.getId(), n);
		}
		
		
		for(Nerc n1 : grafo.vertexSet()) {
			
			for(Nerc n2 : dao.loadAllArchi(n1.getId(), mappaNerc)) {
				if(!grafo.containsEdge(n1, n2)) {
					grafo.addEdge(n1, n2);
					
				}
			}
			
		}
		
		
		
		
		System.out.format("creato un grafo con %d vertici e %d archi", grafo.vertexSet().size(),grafo.edgeSet().size());
		
		
		//aggiungo i pesi agli archi
		
		for(DefaultWeightedEdge e : grafo.edgeSet()) {
			
			grafo.setEdgeWeight(e, dao.GetPeso(grafo.getEdgeSource(e).getId(), grafo.getEdgeTarget(e).getId(), mappaNerc));
			
			System.out.print(e.toString()+" "+grafo.getEdgeWeight(e)+"\n");
			
		}
		
		
		//System.out.print(grafo.edgeSet().toString());
		
		for(Nerc nvic : Graphs.neighborListOf(grafo, scelto)) {
			
			DefaultWeightedEdge e = grafo.getEdge(scelto, nvic);
			vicini.add(new Vicino(grafo.getEdgeWeight(e), nvic));
		}
		
	}
	
	public List<Vicino> getvicini(){
		Collections.sort(this.vicini);
		return vicini;
	}
}
