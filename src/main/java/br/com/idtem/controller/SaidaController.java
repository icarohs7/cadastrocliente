package br.com.idtem.controller;

import com.github.icarohs7.unoxlib.tables.EditableTableModel;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.idtem.model.Cliente;
import br.com.idtem.model.ClienteDAO;

/**
 * Controlador do painel de saída contendo a tabela
 */
public class SaidaController {
	/**
	 * Instância do controller
	 */
	private static final SaidaController INSTANCE;
	
	static {
		INSTANCE = new SaidaController();
	}
	
	
	public static SaidaController getINSTANCE() {
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
		var clienteId = Integer.parseInt(tabela.getValueAt(row, 0).toString());
		EntradaController
				.getINSTANCE()
				.setCliente(clientes.stream()
				                    .filter(c -> c.getId() == clienteId)
				                    .findFirst().orElseThrow());
		BotoesController.getINSTANCE().atualizarEstados();
	}
	
	/**
	 * Sincroniza os dados da tabela com o banco de dados asíncronamente
	 * @return A thread responsável pela sincronização, iniciada automaticamente
	 */
	public synchronized Thread sincronizarTabela() {
		var thr = new Thread(() -> {
			sincronizarClientes();
			var listaArray = clientes.stream()
			                         .map(cliente -> {
				                         return new String[] { String.valueOf(cliente.getId()), cliente.getNome(), cliente.getEmail() };
			                         })
			                         .collect(Collectors.toList());
			tabela.setAllRows(listaArray);
		});
		thr.start();
		return thr;
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
