package br.com.senai.view.horario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import br.com.senai.core.domain.DiaDaSemana;
import br.com.senai.core.domain.Horario;
import br.com.senai.core.domain.Restaurante;
import br.com.senai.core.service.HorarioService;
import br.com.senai.core.service.RestauranteService;
import br.com.senai.view.componentes.table.HorarioTableModel;

public class ViewConfHorario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableHorario;
	private JComboBox<Restaurante> cbRestaurante;
	private JComboBox<DiaDaSemana> cbDiaSemana;
	private RestauranteService service = new RestauranteService();
	private HorarioService horarioService = new HorarioService();
	private JFormattedTextField ftfHoraAbertura;
	private JFormattedTextField ftfHoraFechamento;
	private Horario horario;
	
	public void carregarComboBox() {
		
		List<Restaurante> restaurantes = service.listarTodos();
		for (Restaurante res : restaurantes) {
			cbRestaurante.addItem(res);
		}
		
	}
	
	public void carregarDiaSemana() {
		List<DiaDaSemana> listaDiaSemana = new ArrayList<>();
		for (DiaDaSemana diaDaSemana : DiaDaSemana.values()) {
			listaDiaSemana.add(diaDaSemana);
			cbDiaSemana.addItem(diaDaSemana);
		}
	}

	public ViewConfHorario() {
		setResizable(false);
		HorarioTableModel model = new HorarioTableModel(new ArrayList<Horario>());
		this.tableHorario = new JTable(model);
		tableHorario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setTitle("Gerenciar Horários - Cadastro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 682, 435);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRestaurante = new JLabel("Restaurante");
		lblRestaurante.setBounds(25, 36, 70, 16);
		contentPane.add(lblRestaurante);
		
		cbRestaurante = new JComboBox<Restaurante>();
		cbRestaurante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					
					Restaurante itemSelecionado = (Restaurante) cbRestaurante.getSelectedItem();
					int idRest = itemSelecionado.getId();
					List<Horario> horarioEncontrado = horarioService.listarPor(idRest);
					boolean isRestauranteOK = horarioEncontrado.isEmpty();
					if (isRestauranteOK) {
						JOptionPane.showMessageDialog(contentPane, "Não foi encontrado nenhum restaurante!!");
					} else {
						HorarioTableModel model = new HorarioTableModel(horarioEncontrado);
						tableHorario.setModel(model);
						tableHorario.updateUI();
						configurarTabela();
					}
					
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane, e2.getMessage());
				}
				
			}
		});
		cbRestaurante.setBounds(100, 32, 554, 25);
		contentPane.add(cbRestaurante);
		
		JLabel lblDiaSemana = new JLabel("Dia da Semana");
		lblDiaSemana.setBounds(12, 73, 85, 16);
		contentPane.add(lblDiaSemana);
		
		cbDiaSemana = new JComboBox<>();
		cbDiaSemana.setBounds(100, 69, 117, 25);
		contentPane.add(cbDiaSemana);
		
		JLabel lblAbertura = new JLabel("Abertura");
		lblAbertura.setBounds(227, 69, 56, 16);
		contentPane.add(lblAbertura);
		
		JLabel lblFechamento = new JLabel("Fechamento");
		lblFechamento.setBounds(381, 69, 70, 16);
		contentPane.add(lblFechamento);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
					Restaurante restaurante = (Restaurante) cbRestaurante.getSelectedItem();
					int idRest = restaurante.getId();
					DiaDaSemana diaSemana =  (DiaDaSemana) cbDiaSemana.getSelectedItem();
					LocalTime horaAbertura = LocalTime.parse(ftfHoraAbertura.getText().formatted(dtf));
					LocalTime horaFechamento = LocalTime.parse(ftfHoraFechamento.getText().formatted(dtf));
					
					if (horario == null) {
						horario = new Horario(diaSemana, horaAbertura, horaFechamento, idRest);
						horarioService.salvar(horario);
						JOptionPane.showMessageDialog(contentPane, "Horario adicionado com sucesso!");
						horario = null;
					} else {
						horario.setDiaSemana(diaSemana);
						horario.setHoraAbertura(horaAbertura);
						horario.setHoraFechamento(horaFechamento);
						horario.setRestaurante(idRest);
						horarioService.salvar(horario);
						JOptionPane.showMessageDialog(contentPane, "Horario alterado com sucesso!");
						horario = null;
					}
					
				} catch (Exception er) {
					throw new RuntimeException("Ocorreu um erro ao adicionar um horário ao restaurante. Motivo: " + er.getMessage());
				}
				
			}
		});
		btnAdicionar.setBounds(558, 68, 96, 26);
		contentPane.add(btnAdicionar);
		
		JLabel lblHorarios = new JLabel("Horários");
		lblHorarios.setBounds(12, 123, 56, 16);
		contentPane.add(lblHorarios);
		
		JScrollPane scrollPane = new JScrollPane(tableHorario);
		scrollPane.setBounds(12, 151, 398, 145);
		contentPane.add(scrollPane);
		
		JLabel lblAcoes = new JLabel("Ações");
		lblAcoes.setBounds(428, 151, 36, 16);
		contentPane.add(lblAcoes);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int linhaSelecionada = tableHorario.getSelectedRow();
				boolean isLinhaOK = linhaSelecionada >= 0;
				HorarioTableModel model = (HorarioTableModel) tableHorario.getModel();
				if (isLinhaOK) {
					Horario horarioSelecionado = model.getPor(linhaSelecionada);
					setHorario(horarioSelecionado);
					
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma linha para edição!");
				}
				
			}
		});
		btnEditar.setBounds(439, 179, 199, 26);
		contentPane.add(btnEditar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int linhaSelecionada = tableHorario.getSelectedRow();
				HorarioTableModel model = (HorarioTableModel) tableHorario.getModel();
				boolean isKinhaOk = linhaSelecionada >= 0 && !model.isVazio();
				if (isKinhaOk) {
					
					int confirmExclusao = JOptionPane.showConfirmDialog(contentPane, "Exclusão", "Você realmente deseja excluir o horário selecionado?", JOptionPane.YES_NO_OPTION);
					Horario horarioSelecionada = model.getPor(linhaSelecionada);
					try {
						horarioService.excluir(horarioSelecionada.getId());
						model.removerPor(linhaSelecionada);
						JOptionPane.showMessageDialog(contentPane, "Horário removido com sucesso.");
						tableHorario.updateUI();
						
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(contentPane, e2.getMessage());
					}
					
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma linha para remoção.");
				}
				
			}
		});
		btnExcluir.setBounds(439, 225, 199, 26);
		contentPane.add(btnExcluir);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(542, 337, 96, 26);
		contentPane.add(btnCancelar);
		
		ftfHoraAbertura = new JFormattedTextField();
		ftfHoraAbertura.setBounds(279, 69, 96, 20);
		contentPane.add(ftfHoraAbertura);
		
		ftfHoraFechamento = new JFormattedTextField();
		ftfHoraFechamento.setBounds(454, 69, 96, 20);
		contentPane.add(ftfHoraFechamento);
	
		try {
			MaskFormatter mascaraAbertura = new MaskFormatter("##:##:00");
			MaskFormatter mascaraFechamento = new MaskFormatter("##:##:00");
			mascaraAbertura.install(ftfHoraAbertura);
			mascaraFechamento.install(ftfHoraFechamento);
		} catch (ParseException er) {
			er.printStackTrace();
		}
		
		setLocationRelativeTo(null);
		this.carregarComboBox();
		this.carregarDiaSemana();
	}
	
	public void configurarColuna(int indice, int largura) {
		this.tableHorario.getColumnModel().getColumn(indice).setResizable(false);
		this.tableHorario.getColumnModel().getColumn(indice).setPreferredWidth(largura);
	}
	
	public void configurarTabela() {
		final int COLUNA_DIA_SEMANA = 0;
		final int COLUNA_ABERTURA = 1;
		final int COLUNA_FECHAMENTO = 2;
		this.tableHorario.getTableHeader().setReorderingAllowed(false);
		this.tableHorario.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.configurarColuna(COLUNA_DIA_SEMANA, 150);
		this.configurarColuna(COLUNA_ABERTURA, 250);
		this.configurarColuna(COLUNA_FECHAMENTO, 250);
	}
	
	public void setHorario(Horario horario) {
		this.horario = horario;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		this.cbRestaurante.setSelectedItem(horario.getRestaurante());
		this.cbDiaSemana.setSelectedItem(horario.getDiaSemana());
		this.ftfHoraAbertura.setText(horario.getHoraAbertura().toString());
		this.ftfHoraFechamento.setText(horario.getHoraFechamento().toString());
		//this.ftfHoraAbertura.setText(String.valueOf(ftfHoraAbertura.getText().formatted(dtf)));
		//this.ftfHoraFechamento.setText(String.valueOf(ftfHoraAbertura.getText().formatted(dtf)));
	}
}
