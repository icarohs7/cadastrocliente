package br.com.idtem.controller;

import com.github.icarohs7.unoxlib.tables.EditableTableModel;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.idtem.model.Cliente;
import br.com.idtem.model.ClienteDAO;

public class SaidaController {
	/**
	 * Instância do controller
	 */
	private static final SaidaController INSTANCE;
	
	static {
		INSTANCE = new SaidaController();
	}
	
	
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
		sincronizarTabela();
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
	 * Edita os dados de um cliente
	 * @param row Linha da tabela em que o cliente está contido
	 */
	public void editarCliente(int row) {
		EntradaController
				.getINSTANCE()
				.setCliente(clientes.stream()
				                    .filter(c -> c.getId() == Integer.parseInt(tabela.getValueAt(row, 0).toString()))
				                    .findFirst().orElseThrow());
		
	}
	
	public synchronized void sincronizarTabela() {
		new Thread(() -> {
			sincronizarClientes();
			var listaArray = clientes.stream()
			                         .map(cliente -> {
				                         return new String[] { String.valueOf(cliente.getId()), cliente.getNome(), cliente.getEmail() };
			                         })
			                         .collect(Collectors.toList());
			tabela.setAllRows(listaArray);
		}).start();
	}
	
	/**
	 * Sincroniza os clientes locais com o banco
	 */
	private void sincronizarClientes() {
		var novaLista = ClienteDAO.getINSTANCE().getClientes();
		clientes.clear();
		clientes.addAll(novaLista);
	}
	
	public EditableTableModel getTabela() {
		return tabela;
	}
	
	public List<Cliente> getClientes() {
		return clientes;
	}
}
