package br.com.senai.view.componentes.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.senai.core.domain.Horario;

public class HorarioTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	private final int NUM_COL = 3;
	
	private List<Horario> horarios;
	
	public Horario getPor(int rowIndex) {
		return horarios.get(rowIndex);
	}
	
	public String getColumnName(int columnIndex) {
		if (columnIndex == 0) {
			return "Dia da Semana";
		} else if(columnIndex == 1) {
			return "Abertura";
		} else if(columnIndex == 3){
			return "Fechamento";
		}
		throw new IllegalArgumentException("Índice inválido.");
	}
	
	@Override
	public int getRowCount() {
		return horarios.size();
	}

	@Override
	public int getColumnCount() {
		return NUM_COL;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Horario horarioDaLinha = horarios.get(rowIndex);
		if (columnIndex == 0) {
			return horarioDaLinha.getDiaSemana();
		} else if (columnIndex == 1) {
			return horarioDaLinha.getHoraAbertura();
		} else if (columnIndex == 2) {
			return horarioDaLinha.getHoraFechamento();
		}
		throw new IllegalArgumentException("Índice inválido");
	}

	public void removerPor(int rowIndex) {
		this.horarios.remove(rowIndex);
	}
	
	public boolean isVazio() {
		return horarios.isEmpty();
	}
	
}
