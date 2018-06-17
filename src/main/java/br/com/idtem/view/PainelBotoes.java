package br.com.idtem.view;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
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
		var lblMsg = new JLabel(" ");
		/* Adicionar botões ao painel */
		add(controller.getBotoes().get("Inserir"));
		add(controller.getBotoes().get("Remover"));
		add(controller.getBotoes().get("Alterar"));
		add(controller.getBotoes().get("Confirmar"));
		add(controller.getBotoes().get("Cancelar"));
		add(lblMsg, "growx");
		add(controller.getBotoes().get("Sair"));
		
		/* Listener da mensagem de confirmação */
		controller.confirmandoProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				lblMsg.setVisible(true);
				switch (controller.tipoConfirmacao) {
					case INSERIR:
						lblMsg.setText("Confirmar insercao?");
						break;
					case ALTERAR:
						lblMsg.setText("Confirmar alteracao?");
						break;
					case REMOVER:
						lblMsg.setText("Confirmar remocao?");
						break;
				}
			} else {
				lblMsg.setVisible(false);
			}
		});
	}
}
