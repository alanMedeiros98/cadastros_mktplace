package br.com.senai.core.service;

import br.com.senai.core.dao.DaoHorario;
import br.com.senai.core.dao.FactoryDao;

public class HorarioService {

	DaoHorario dao;
	
	HorarioService(){
		this.dao = FactoryDao.getInstance().getDaoHorario();
	}
	
}
