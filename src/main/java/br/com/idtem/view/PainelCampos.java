package br.com.idtem.view;

import net.miginfocom.swing.MigLayout;

import javax.swing.JPanel;

import br.com.idtem.controller.EntradaController;
import br.com.idtem.model.Cliente;

/**
 * Painel contendo os campos do formulário
 */
class PainelCampos extends JPanel {
	private EntradaController controller = EntradaController.getINSTANCE();
	
	/**
	 * Inicialização do painel
	 */
	PainelCampos() {
		super(new MigLayout("nogrid, fillx"));
		criarComponentes();
	}
	
	/**
	 * Processo de criação dos componentes
	 */
	private void criarComponentes() {
		
		/* Posicionar campos no painel */
		add(controller.getCampos().get("ID"), "w 10%");
		add(controller.getCampos().get("Nome"), "w 90%, wrap");
		add(controller.getCampos().get("Tel. Residencial"), "w 100%");
		add(controller.getCampos().get("Tel. Comercial"), "w 100%");
		add(controller.getCampos().get("Tel. Celular"), "w 100%, wrap");
		add(controller.getCampos().get("E-mail"), "w 100%");
		
		/* Configurações extras dos campos */
		controller.getCampos().get("ID").getCampo().setEnabled(false);
		
		/* Atrelar campos ao objeto do controller */
		controller.getCampos().get("ID").bind(controller.getCliente().idProperty());
		controller.getCampos().get("Nome").bind(controller.getCliente().nomeProperty());
		controller.getCampos().get("Tel. Residencial").bind(controller.getCliente().telefoneResidencialProperty());
		controller.getCampos().get("Tel. Comercial").bind(controller.getCliente().telefoneComercialProperty());
		controller.getCampos().get("Tel. Celular").bind(controller.getCliente().telefoneCelularProperty());
		controller.getCampos().get("E-mail").bind(controller.getCliente().emailProperty());
		
		/* Mostrar dados do cliente na inicialização */
		atualizarCliente();
	}
	
	/**
	 * Define o valor dos campos para o valor das propriedades de um cliente
	 * @param cliente Cliente usado
	 */
	public void mostrarCliente(Cliente cliente) {
		controller.getCampos().get("ID").getCampo().setText(String.valueOf(cliente.getId()));
		
		if (!cliente.getNome().isEmpty()) {
			controller.getCampos().get("Nome").getCampo().setText(cliente.getNome());
		}
		
		if (!cliente.getTelefoneResidencial().isEmpty()) {
			controller.getCampos().get("Tel. Residencial").getCampo().setText(cliente.getTelefoneResidencial());
		}
		
		if (!cliente.getTelefoneComercial().isEmpty()) {
			controller.getCampos().get("Tel. Comercial").getCampo().setText(cliente.getTelefoneComercial());
		}
		
		if (!cliente.getTelefoneCelular().isEmpty()) {
			controller.getCampos().get("Tel. Celular").getCampo().setText(cliente.getTelefoneCelular());
		}
		
		if (!cliente.getEmail().isEmpty()) {
			controller.getCampos().get("E-mail").getCampo().setText(cliente.getEmail());
		}
	}
	
	/**
	 * Atualiza os valores nos campos para o valor definido no cliente do controller
	 */
	public void atualizarCliente() {
		mostrarCliente(controller.getCliente());
	}
}
