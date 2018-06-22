package com.github.icarohs7.cadastrocliente.tabela;

import com.github.icarohs7.cadastrocliente.formulario.FormularioController;
import com.github.icarohs7.cadastrocliente.model.ClienteDAO;
import com.github.icarohs7.unoxlib.swing.tables.NXTable;
import com.github.icarohs7.unoxlib.swing.tables.NXTableModel;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Controller da tabela
 */
public class TabelaController {
	private final ClienteDAO dao = ClienteDAO.getINSTANCE();
	/**
	 * Dados da tabela
	 */
	private NXTableModel tModel;
	/**
	 * View associada ao controller
	 */
	private TabelaView view;
	
	private TabelaController() {
		tModel = new NXTableModel(new LinkedList<String[]>(), new String[] { "ID", "Nome", "E-mail" });
		atualizarTabela();
		
		/* Escutar eventos de alteração dos clientes registrados */
		dao.addObserver(this::atualizarTabela);
		
		/* Listener para cliques nas linhas da tabela */
		view = new TabelaView(NXTable.ofACustomModel(tModel).getScrollableTable(), (row, column) -> {
			FormularioController.getInstance().setCliente(dao.getClientes().get(row));
		});
	}
	
	/**
	 * Atualizar os dados da tabela com os dados do
	 * banco de forma asíncrona
	 */
	private void atualizarTabela(Object... args) {
		new Thread(() -> tModel.setAllRows(dao.pesquisar().stream()
		                                      .map(cliente -> new String[] { cliente.getId(), cliente.getNome(), cliente.getEmail() })
		                                      .collect(Collectors.toList()))).start();
	}
	
	public TabelaView getView() { return view; }
	
	private static final TabelaController INSTANCE;
	
	static { INSTANCE = new TabelaController(); }
	
	public static TabelaController getInstance() { return INSTANCE; }
}
