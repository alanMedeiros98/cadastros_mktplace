package br.com.senai.core.service;

import java.util.List;

import javax.swing.JOptionPane;

import br.com.senai.core.dao.DaoHorario;
import br.com.senai.core.dao.FactoryDao;
import br.com.senai.core.domain.Horario;

public class HorarioService {

	DaoHorario dao;
	
	public HorarioService(){
		this.dao = FactoryDao.getInstance().getDaoHorario();
	}
	
	public void salvar(Horario horario) {
		this.validar(horario);
		boolean isHorarioInserido = horario.getId() > 0;
		if (isHorarioInserido) {
			this.dao.alterar(horario);
		} else {
			this.dao.inserir(horario);			
		}
	}
	
	public List<Horario> listarPor(int idRestaurante){
			
			return dao.listarPorRestaurante(idRestaurante);
			
	}
	
	public void excluir(int idHorario) {
		
		if (idHorario > 0) {
			this.dao.excluirPor(idHorario);
		}
		
	}
	
	public void validar(Horario horario) {
		
		if (horario != null) {
			boolean isRestauranteInvalido = horario.getRestaurante() <= 0;
			boolean isDiaDaSemanaInvalido = horario.getDiaSemana() == null;
			boolean isHorarioAberturaInvalido = horario.getHoraAbertura() == null;
			boolean isHorarioFechamentoInvalido = horario.getHoraFechamento() == null;
			
			if (isRestauranteInvalido) {
				throw new NullPointerException("O Restaurante não pode ser nulo.");
			}
			
			if (isDiaDaSemanaInvalido) {
				throw new NullPointerException("O dia da semana não pode ser nulo.");
			}
			
			if (isHorarioAberturaInvalido) {
				throw new NullPointerException("O horário de abertura não pode ser nulo.");
			}
			
			if (isHorarioFechamentoInvalido) {
				throw new NullPointerException("O horário de fechamento não pode ser nulo.");
			}
		} else {
			throw new NullPointerException("O horaio não pode ser nulo.");
		}
		
	}
}
