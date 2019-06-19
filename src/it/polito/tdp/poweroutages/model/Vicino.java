package it.polito.tdp.poweroutages.model;

public class Vicino implements Comparable<Vicino>{

	private double peso;
	private Nerc vicino;
	
	
	public double getPeso() {
		return peso;
	}
	public Nerc getVicino() {
		return vicino;
	}
	public Vicino(double peso, Nerc vicino) {
		super();
		this.peso = peso;
		this.vicino = vicino;
	}
	@Override
	public int compareTo(Vicino o) {
		// TODO Auto-generated method stub
		return (int) (this.peso-o.getPeso());
	}
	@Override
	public String toString() {
		return String.format(" peso=%s, vicino=%s ", peso, vicino);
	}
	
	
	
	
}
