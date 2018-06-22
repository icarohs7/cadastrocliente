package com.github.icarohs7.cadastrocliente.controles;

import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 * View contendo o painel de controles
 */
class ControlesView extends JPanel {
	
	/**
	 * Compor o painel
	 * @param botoes Botões a serem mostrados no painel
	 */
	ControlesView(Map<String, JButton> botoes) {
		super(new MigLayout("nogrid"));
		
		// Igualar o tamanho dos botões ao maior tamanho entre os mesmos
		@SuppressWarnings("OptionalGetWithoutIsPresent")
		var maiorDimensao = botoes.values()
		                          .stream()
		                          .map(JButton::getPreferredSize)
		                          .reduce((acc, it) -> acc.width > it.width ? acc : it)
		                          .get();
		botoes.values().forEach(button -> button.setMinimumSize(maiorDimensao));
		
		add(botoes.get("inserir"));
		add(botoes.get("remover"));
		add(botoes.get("alterar"));
		add(botoes.get("confirmar"));
		add(botoes.get("cancelar"));
		add(new JPanel(), "w 100%"); // Componente invisível utilizado para espaçamento
		add(botoes.get("sair"));
	}
}
