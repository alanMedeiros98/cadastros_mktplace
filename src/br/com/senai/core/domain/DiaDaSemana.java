package br.com.senai.core.domain;

public enum DiaDaSemana {

	DOMINGO("Domingo"),
	SEGUNDA("Segunda"),
	TERCA("Terça"),
	QUARTA("Quarta"),
	QUINTA("Quinta"),
	SEXTA("Sexta"),
	SABADO("Sábado");

	private String diaDaSemana;
	
	DiaDaSemana(String dia) {
		this.diaDaSemana = dia;
	}
	
	public String getDiaSemana() {
		return diaDaSemana;
	}
	
}
