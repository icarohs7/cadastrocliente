package com.github.icarohs7.cadastrocliente.controles;

import java.util.Map;
import javax.swing.JButton;

/**
 * Controller do painel de controles
 */
public class ControlesController {
	/**
	 * View atrelada ao controller
	 */
	private ControlesView view;
	/**
	 * Handler para os eventos dos botões
	 */
	private ControlesHandler handler;
	/**
	 * Conjunto de botões
	 */
	private Map<String, JButton> botoes = Map.of(
			"inserir", new JButton("Inserir"),
			"remover", new JButton("Remover"),
			"alterar", new JButton("Alterar"),
			"confirmar", new JButton("Confirmar"),
			"cancelar", new JButton("Cancelar"),
			"sair", new JButton("Sair"));
	
	private ControlesController() {
		view = new ControlesView(botoes);
		handler = new ControlesHandler(botoes);
	}
	
	public ControlesView getView() { return view; }
	
	private static final ControlesController INSTANCE;
	
	static { INSTANCE = new ControlesController(); }
	
	public static ControlesController getInstance() { return INSTANCE; }
}
