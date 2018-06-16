package br.com.idtem.controller;

import com.github.icarohs7.unoxlib.tables.EditableTableModel;

import java.util.LinkedList;
import java.util.List;

import br.com.idtem.model.Cliente;

public class SaidaController {
	/* Instância do controller */
	private static final SaidaController INSTANCE = new SaidaController();
	
	
	public static SaidaController getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Modelo contendo os dados da tabela
	 */
	private final EditableTableModel tabela = new EditableTableModel(
			new String[][] {},
			new String[] { "ID", "Nome", "E-mail" });
	
	/**
	 * Objeto cacheando os clientes do banco, para reduzir o número de requisições
	 */
	private List<Cliente> clientes = new LinkedList<>();
	
	private SaidaController() {
	}
	
	/**
	 * Verifica se um cliente está registrado
	 * @param id Identificador Único do cliente
	 * @return verdadeiro se o cliente existe e falso do contrário
	 */
	public boolean clienteExists(int id) {
		var clientesEncontrados = clientes.stream()
		                                  .filter(c -> c.getId() == id)
		                                  .count();
		
		/* Se houver mais de uma chave para o mesmo cliente, lançar um erro */
		if (clientesEncontrados > 1) { throw new VerifyError("O banco contém chaves duplicadas"); }
		
		return clientesEncontrados > 0;
	}
	
	/**
	 * Sincroniza os clientes locais com o banco
	 */
	public void sincronizarClientes() {
		/* TODO: Sincronização de clientes com o banco */
	}
	
	public EditableTableModel getTabela() {
		return tabela;
	}
	
	public List<Cliente> getClientes() {
		return clientes;
	}
}
