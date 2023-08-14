package br.com.senai.core.domain;

import java.time.LocalTime;
import java.util.Objects;

public class Horario {

	private int id;
	private DiaDaSemana diaSemana;
	private LocalTime horaAbertura;
	private LocalTime horaFechamento;
	private int restaurante;
	
	
	public Horario(DiaDaSemana diaSemana, LocalTime horaAbertura, LocalTime horaFechamento, int restaurante) {
		this.diaSemana = diaSemana;
		this.horaAbertura = horaAbertura;
		this.horaFechamento = horaFechamento;
		this.restaurante = restaurante;
	}

	public Horario(int id, DiaDaSemana diaSemana, LocalTime horaAbertura, LocalTime horaFechamento, int restaurante) {
		this(diaSemana, horaAbertura, horaFechamento, restaurante);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public DiaDaSemana getDiaSemana() {
		return diaSemana;
	}
	public void setDiaSemana(DiaDaSemana diaSemana) {
		this.diaSemana = diaSemana;
	}
	public LocalTime getHoraAbertura() {
		return horaAbertura;
	}
	public void setHoraAbertura(LocalTime horaAbertura) {
		this.horaAbertura = horaAbertura;
	}
	public LocalTime getHoraFechamento() {
		return horaFechamento;
	}
	public void setHoraFechamento(LocalTime horaFechamento) {
		this.horaFechamento = horaFechamento;
	}
	public int getRestaurante() {
		return restaurante;
	}
	public void setRestaurante(int restaurante) {
		this.restaurante = restaurante;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Horario other = (Horario) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Horario [diaSemana=" + diaSemana + ", horaAbertura=" + horaAbertura + ", horaFechamento="
				+ horaFechamento + ", restaurante=" + restaurante + "]";
	}
	
	
	
}
