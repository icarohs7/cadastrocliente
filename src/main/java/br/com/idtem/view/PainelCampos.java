package br.com.idtem.view;

import net.miginfocom.swing.MigLayout;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import br.com.idtem.controller.EntradaController;
import br.com.idtem.model.Cliente;

/**
 * Painel contendo os campos do formulário
 */
class PainelCampos extends JPanel {
	private final Map<String, Campo> campos = new HashMap<>();
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
		/* Gerar campos */
		gerarCampos();
		
		/* Posicionar campos no painel */
		add(campos.get("ID"));
		campos.get("ID").setLargura(60);
		campos.get("ID").getCampo().setEnabled(false);
		add(campos.get("Nome"), "growx,spanx,wrap");
		add(campos.get("Tel. Residencial"), "growx 1");
		add(campos.get("Tel. Comercial"), "growx 1");
		add(campos.get("Tel. Celular"), "growx 1, wrap");
		add(campos.get("E-mail"), "growx");
		
		/* Atrelar campos ao objeto do controller */
		campos.get("ID").bind(controller.getCliente().idProperty());
		campos.get("Nome").bind(controller.getCliente().nomeProperty());
		campos.get("Tel. Residencial").bind(controller.getCliente().telefoneResidencialProperty());
		campos.get("Tel. Comercial").bind(controller.getCliente().telefoneComercialProperty());
		campos.get("Tel. Celular").bind(controller.getCliente().telefoneCelularProperty());
		campos.get("E-mail").bind(controller.getCliente().emailProperty());
		
		/* Mostrar dados do cliente na inicialização */
		atualizarCliente();
	}
	
	/**
	 * Define o valor dos campos para o valor das propriedades de um cliente
	 * @param cliente Cliente usado
	 */
	public void mostrarCliente(Cliente cliente) {
		campos.get("ID").getCampo().setText(String.valueOf(cliente.getId()));
		
		if (!cliente.getNome().isEmpty()) {
			campos.get("Nome").getCampo().setText(cliente.getNome());
		}
		
		if (!cliente.getTelefoneResidencial().isEmpty()) {
			campos.get("Tel. Residencial").getCampo().setText(cliente.getTelefoneResidencial());
		}
		
		if (!cliente.getTelefoneComercial().isEmpty()) {
			campos.get("Tel. Comercial").getCampo().setText(cliente.getTelefoneComercial());
		}
		
		if (!cliente.getTelefoneCelular().isEmpty()) {
			campos.get("Tel. Celular").getCampo().setText(cliente.getTelefoneCelular());
		}
		
		if (!cliente.getEmail().isEmpty()) {
			campos.get("E-mail").getCampo().setText(cliente.getEmail());
		}
	}
	
	/**
	 * Atualiza os valores nos campos para o valor definido no cliente do controller
	 */
	public void atualizarCliente() {
		mostrarCliente(controller.getCliente());
	}
	
	/**
	 * Gera os campos do formulário
	 */
	private void gerarCampos() {
		/* Instâciar campos */
		for (String nomeCampo : controller.NOMES_CAMPOS) {
			campos.put(nomeCampo, new Campo(nomeCampo));
		}
	}
}
