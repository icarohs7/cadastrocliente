package br.com.idtem.view;

import net.miginfocom.swing.MigLayout;

import java.awt.Dimension;

import javax.swing.JButton;
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
		gerarBotoes();
		posicionarBotoes();
		registrarListeners();
		atrelarBotoes();
		
		/* Definição dos estados iniciais */
		controller.atualizarEstados();
	}
	
	/**
	 * Registrar listeners dos botões
	 */
	private void registrarListeners() {
		controller.getBotoes().get("Inserir").addActionListener(evt -> controller.inserir());
		controller.getBotoes().get("Remover").addActionListener(evt -> controller.remover());
		controller.getBotoes().get("Alterar").addActionListener(evt -> controller.alterar());
		controller.getBotoes().get("Confirmar").addActionListener(evt -> controller.confirmar());
		controller.getBotoes().get("Cancelar").addActionListener(evt -> controller.cancelar());
		controller.getBotoes().get("Sair").addActionListener(evt -> System.exit(0));
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
	
	/**
	 * Atrela os botões as propriedades de visibilidade do controller
	 */
	private void atrelarBotoes() {
		controller.confirmandoProperty().addListener((observable, oldValue, newValue) -> controller.atualizarEstados());
	}
	
	/**
	 * Gerar mapa de botões
	 */
	private void gerarBotoes() {
		for (String nomeBotao : controller.NOMES_BOTOES) {
			controller.getBotoes().put(nomeBotao, new JButton(nomeBotao));
			controller.getBotoes().get(nomeBotao).setPreferredSize(new Dimension(100, 30));
		}
	}
}
