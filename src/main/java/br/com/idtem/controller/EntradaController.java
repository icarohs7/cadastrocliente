package br.com.idtem.controller;

import br.com.idtem.model.Cliente;

public class EntradaController {
	/**
	 * Instância do controller
	 */
	private static final EntradaController INSTANCE = new EntradaController();
	
	public static EntradaController getINSTANCE() {
		return INSTANCE;
	}
	
	/**
	 * Nomes dos campos do formulário
	 */
	public final String[] NOMES_CAMPOS = {
			"ID", "Nome", "Tel. Residencial",
			"Tel. Comercial", "Tel. Celular", "E-mail"
	};
	/**
	 * Cliente sendo editado
	 */
	private Cliente cliente;
	
	private EntradaController() {
	}
	
	public Cliente getCliente() {
		if (cliente == null) {
			cliente = new Cliente();
		}
		return cliente;
	}
}
