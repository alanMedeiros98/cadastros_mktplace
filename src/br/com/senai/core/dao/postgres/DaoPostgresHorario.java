package br.com.senai.core.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import br.com.senai.core.dao.DaoHorario;
import br.com.senai.core.dao.ManagerDb;
import br.com.senai.core.domain.DiaDaSemana;
import br.com.senai.core.domain.Horario;

public class DaoPostgresHorario implements DaoHorario {

	private final String INSERT = "INSERT INTO horarios_atendimento (dia_semana, hora_abertura, hora_fechamento, id_restaurante) VALUES (?, ?, ?, ?)";
	private final String UPDATE = "UPDATE horarios_atendimento SET dia_semana = ?, hora_abertura = ?, hora_fechamento = ?, id_restaurante = ? WHERE id = ?";
	private final String SELECT_BY_REST = "SELECT * FROM horarios_atendimento WHERE id_restaurante = ?";
	private final String DELETE = "DELETE FROM horarios_atendimento WHERE id = ?";
	private final String COUNT_BY_REST = "SELECT Count(*) qtde "
										+ "FROM horarios_atendimento h "
										+ "WHERE h.id_restaurante = ?";
	
	private Connection conexao;
	
	public DaoPostgresHorario() {
		this.conexao = ManagerDb.getInstance().getConexao();
	}
	
	@Override
	public void inserir(Horario horario) {
		PreparedStatement ps = null;
		
		try {
			
			ps = conexao.prepareStatement(INSERT);
			
			ps.setString(1, horario.getDiaSemana().name());
			ps.setTime(2, Time.valueOf(horario.getHoraAbertura()));
			ps.setTime(3, Time.valueOf(horario.getHoraFechamento()));
			ps.setInt(4, horario.getRestaurante());
			
			ps.execute();
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao inserir o horario do atendimento. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
		}

	}

	@Override
	public void alterar(Horario horario) {
		
		PreparedStatement ps =null;
				
				try {
					ManagerDb.getInstance().configurarAutocommitDa(conexao, false);
					
					ps = conexao.prepareStatement(UPDATE);
					ps.setString(1, horario.getDiaSemana().name());
					ps.setTime(2, Time.valueOf(horario.getHoraAbertura()));
					ps.setTime(3, Time.valueOf(horario.getHoraFechamento()));
					ps.setInt(4, horario.getRestaurante());
					
					boolean isAlteracaoOk = ps.executeUpdate() ==1;
					
					if (isAlteracaoOk) {
						this.conexao.commit();
					} else {
						this.conexao.rollback();
					}
					
					ManagerDb.getInstance().configurarAutocommitDa(conexao, true);
					
					
				} catch (Exception e) {
					throw new RuntimeException("Ocorreu um erro ao alterar o horario. Motivo: " + e.getMessage());
				} finally {
					ManagerDb.getInstance().fechar(ps);
				}

	}

	@Override
	public void excluirPor(int id) {
		PreparedStatement ps = null;
		
		try {
			ManagerDb.getInstance().configurarAutocommitDa(conexao, false);
			
			ps = conexao.prepareStatement(DELETE);
			ps.setInt(1, id);
			boolean isExclusaoOk = ps.executeUpdate() == 1;
			
			if(isExclusaoOk) {
				this.conexao.commit();
				System.out.println("chegou no dao");
			} else {
				this.conexao.rollback();
			}
			ManagerDb.getInstance().configurarAutocommitDa(conexao, true);
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao excluir o horário. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
		}

	}

	@Override
	public int contarPor(int idDoRestaurante) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			ps = conexao.prepareStatement(COUNT_BY_REST);
			ps.setInt(1, idDoRestaurante);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("qtde");
			}
			return 0;
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao contar os horarios. Motivo: " + e.getMessage());
		}finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
			
		}
		
	}
	
	public List<Horario> listarPorRestaurante(int idRestaurante) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Horario> horarios = new ArrayList<Horario>();
		
		try {
			
			ps = conexao.prepareStatement(SELECT_BY_REST);
			ps.setInt(1, idRestaurante);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				horarios.add(extrairDo(rs));
			}
			
			return horarios;
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao listar os horarios. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
		}
		
		
	}
	
	private Horario extrairDo(ResultSet rs) {
		
		try {
			
			DiaDaSemana diaSemana = DiaDaSemana.valueOf(rs.getString("dia_semana"));
			LocalTime horaAbertura = rs.getTime("hora_abertura").toLocalTime();
			LocalTime horaFechamento = rs.getTime("hora_fechamento").toLocalTime();
			int idDoRestaurante = rs.getInt("id_restaurante");
			
			return new Horario(diaSemana, horaAbertura, horaFechamento, idDoRestaurante);
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao extrair os horarios. Motivo: " + e.getMessage());
		}
		
	}

}
