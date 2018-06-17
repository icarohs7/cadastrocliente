package br.com.idtem.controller;

import java.util.HashMap;
import java.util.Map;

import br.com.idtem.model.Cliente;
import br.com.idtem.view.Campo;

/**
 * Controlador do painel de entrada contendo os campos
 */
public class EntradaController {
	/**
	 * Instância do controller
	 */
	private static final EntradaController INSTANCE;
	
	static {
		INSTANCE = new EntradaController();
	}
	
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
	 * Campos de entrada
	 */
	private final Map<String, Campo> campos = new HashMap<>();
	/**
	 * Cliente sendo editado
	 */
	private Cliente cliente = new Cliente();
	
	private EntradaController() {
		/* Criar campos */
		for (String nomeCampo : NOMES_CAMPOS) {
			campos.put(nomeCampo, new Campo(nomeCampo));
		}
	}
	
	/**
	 * Limpa os dados do cliente atual
	 */
	public void limparCliente() {
		cliente.clear();
	}
	
	/**
	 * Define a ativação ou desativação de todos os campos
	 * @param enabled Se os campos estão ou não ativados
	 */
	public void setAllCamposEnabled(boolean enabled) {
		campos.forEach((nome, campo) -> {
			if (!nome.equals("ID")) {
				campo.getCampo().setEnabled(enabled);
			}
		});
	}
	
	public Map<String, Campo> getCampos() {
		return campos;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente.setAll(cliente);
	}
}
