package br.com.idtem.view;

import net.miginfocom.swing.MigLayout;

import javax.swing.JPanel;

import br.com.idtem.controller.BotoesController;

/**
 * Painel contendo os botões do formulário
 */
class PainelBotoes extends JPanel {
	private BotoesController controller = BotoesController.getINSTANCE();
	
	/**
	 * Inicialização do painel
	 */
	PainelBotoes() {
		super(new MigLayout("fillx, nogrid, ins 0 7"));
		criarComponentes();
	}
	
	/**
	 * Criação de componentes
	 */
	private void criarComponentes() {
		posicionarBotoes();
		
		/* Definição dos estados iniciais */
		controller.atualizarEstados();
	}
	
	/**
	 * Posicionar botões no painel
	 */
	private void posicionarBotoes() {
		/* Adicionar botões ao painel */
		add(controller.getBotoes().get("Inserir"));
		add(controller.getBotoes().get("Remover"));
		add(controller.getBotoes().get("Alterar"));
		add(controller.getBotoes().get("Confirmar"));
		add(controller.getBotoes().get("Cancelar"));
		add(controller.getBotoes().get("Sair"), "east,  gapx 0 7, gapy 0 7");
		controller.getBotoes().get("Sair").setMaximumSize(controller.getBotoes().get("Sair").getPreferredSize());
	}
}
