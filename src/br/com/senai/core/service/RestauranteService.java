package br.com.senai.core.service;

import java.util.List;

import br.com.senai.core.dao.DaoRestaurante;
import br.com.senai.core.dao.FactoryDao;
import br.com.senai.core.domain.Categoria;
import br.com.senai.core.domain.Restaurante;

public class RestauranteService {
	
	private DaoRestaurante dao;
	
	public RestauranteService(){
		this.dao = FactoryDao.getInstance().getDaoRestaurante();
	}

	public void salvar(Restaurante restaurante) {
		this.validar(restaurante);
		boolean isJaInserido = restaurante.getId() > 0;
		
		if(isJaInserido) {
			this.dao.alterar(restaurante);
		} else {
			dao.inserir(restaurante);
		}
		
	}
	
	public void removerPor(int id) {
		if(id > 0) {
			this.dao.excluirPor(id);
			
		}else {
			throw new IllegalArgumentException("O id do restaurante deve ser maior que 0");
		}
		
	}
	
	public Restaurante buscarPor(int id) {
		if(id > 0) {
			Restaurante restauranteEncontrado = this.dao.buscarPor(id);
			if(restauranteEncontrado == null) {
				throw new IllegalArgumentException("Não foi encontrado o restaurante para o código informado");
			}
			return restauranteEncontrado;
		}else {
			throw new IllegalArgumentException("O id do restaurante deve ser maior que 0");
		}
	}
	
	public List<Restaurante> listarPor(String nome, Categoria categoria){
		
		boolean isCategoriaInformada = categoria != null && categoria.getId() > 0;
		
		boolean isNomeInformado = nome != null && !nome.isBlank();
		
		if (!isCategoriaInformada && !isNomeInformado) {
			throw new IllegalArgumentException("informe o nome e/ou categoria para listagem.");
		}
		
		String filtroNome = "";
		
		if (categoria != null && categoria.getId() > 0) {
			filtroNome = nome + "%";
		} else {
			filtroNome = "%" + nome + "%";
		}
		
		return dao.listarPor(filtroNome, categoria);
		
		/*if(nome != null && nome.length() >= 3 && categoria != null) {
			return this.dao.listarPor("%" + nome + "%", categoria);
		}
		throw new IllegalArgumentException("O filtro é obrigatório e deve conter mais de 2 caracteries");*/
	}
	
	public List<Restaurante> listarTodos(){
		return dao.listarPor("%%", null);
	}
	
	private void validar(Restaurante restaurante) {
		if(restaurante != null) {
			boolean isNomeInvalido = restaurante.getNome() == null
					|| restaurante.getNome().isBlank()
					|| restaurante.getNome().length() > 250;
			boolean isDescricaoInvalido = restaurante.getDescricao() == null
					|| restaurante.getDescricao().isBlank();
			boolean isEnderecoInvalido = restaurante.getEndereco() != null;
			boolean isCidadeInvalido = restaurante.getEndereco().getCidade() == null
					|| restaurante.getEndereco().getCidade().isBlank()
					|| restaurante.getEndereco().getCidade().length() > 80;
			boolean isLogradouroInvalido = restaurante.getEndereco().getLogradouro() == null
					|| restaurante.getEndereco().getLogradouro().isBlank()
					|| restaurante.getEndereco().getLogradouro().length() > 200;
			boolean isBairroInvalido = restaurante.getEndereco().getBairro() == null
					|| restaurante.getEndereco().getBairro().isBlank()
					|| restaurante.getEndereco().getBairro().length() > 250;
			boolean isCategoriaInvalido = restaurante.getCategoria().getNome() == null
					&& restaurante.getCategoria().getId() > 0;
			
			if(isNomeInvalido) {
				throw new IllegalArgumentException("O nome do restaurante é obrigatório e não deve possuir mais de 250 caracteres");
			}
			
			if (isEnderecoInvalido) {
				throw new NullPointerException("O endereço não pode ser nulo.");
			}
			
			if (isDescricaoInvalido) {
				throw new IllegalArgumentException("A descrição é obriagatória.");
			}
			
			if (isCidadeInvalido) {
				throw new IllegalArgumentException("A cidade do restaurante é obrigatório e não pode possuir mais de 80 caracteres.");
			}
			
			if (isLogradouroInvalido) {
				throw new IllegalArgumentException("O logradouro é obrigatório e não pode possuir mais de 200 caracteres.");
			}
			
			if (isBairroInvalido) {
				throw new IllegalArgumentException("O bairro é obrigatório e não pode possuir mais de 250 caracteres.");
			}
			
			if (isCategoriaInvalido) {
				throw new IllegalArgumentException("A categoria do restaurante não pode ser nula.");
			}
			
		} else {
			throw new NullPointerException("O restaurante não pode ser nulo");
		}
	}
}
