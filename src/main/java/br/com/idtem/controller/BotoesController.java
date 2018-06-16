package br.com.idtem.controller;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class BotoesController {
	/**
	 * Instância do controller
	 */
	private static final BotoesController INSTANCE = new BotoesController();
	
	public static BotoesController getINSTANCE() {
		return INSTANCE;
	}
	
	/**
	 * Nome dos botões do formulário
	 */
	public final String[] NOMES_BOTOES = {
			"Inserir", "Remover", "Alterar", "Confirmar", "Cancelar", "Sair"
	};
	/**
	 * Botões presentes no formulário
	 */
	private final Map<String, JButton> botoes = new HashMap<>();
	/**
	 * Controlador dos dados de entrada
	 */
	private final EntradaController entrada = EntradaController.getINSTANCE();
	/**
	 * Controlador dos dados de saída
	 */
	private final SaidaController saida = SaidaController.getInstance();
	/**
	 * Tipo de confirmação
	 */
	public TipoConfirmacao tipoConfirmacao;
	/**
	 * Se há uma operação esperando confirmação no momento
	 */
	private BooleanProperty confirmando = new SimpleBooleanProperty(false);
	
	
	private BotoesController() {
	}
	
	public Map<String, JButton> getBotoes() {
		return botoes;
	}
	
	/**
	 * Inicializa o processo de inserção de um registro
	 */
	public void inserir() {
		/* Inserção só é permitida quando o cliente tem todos os seus
		 * dados preenchidos */
		if (entrada.getCliente().isValido()) {
			tipoConfirmacao = TipoConfirmacao.INSERIR;
			setConfirmando(true);
		} else {
			JOptionPane.showMessageDialog(null, "Preencha todos os dados do cliente antes de inseri-lo no banco.");
		}
	}
	
	/**
	 * Inicializa o processo de remoção de um registro
	 */
	public void remover() {
		/* Remoção só é permitida quando o cliente tem todos os seus
		 * dados preenchidos e o seu id existe na base de dados */
		if (entrada.getCliente().isValido() && saida.clienteExists(entrada.getCliente().getId())) {
			tipoConfirmacao = TipoConfirmacao.REMOVER;
			setConfirmando(true);
		} else {
			JOptionPane.showMessageDialog(null, "Preencha todos os dados do cliente e certifique-se que ele existe " +
			                                    "antes de remove-lo do banco.");
		}
	}
	
	/**
	 * Inicializa o processo de alteração de um registro
	 */
	public void alterar() {
		if (entrada.getCliente().isValido() && saida.clienteExists(entrada.getCliente().getId())) {
			tipoConfirmacao = TipoConfirmacao.ALTERAR;
			setConfirmando(true);
		} else {
			JOptionPane.showMessageDialog(null, "Preencha todos os dados do cliente e certifique-se que ele existe " +
			                                    "antes de altera-lo.");
		}
	}
	
	/**
	 * Confirma a ação em andamento
	 */
	public void confirmar() {
		setConfirmando(false);
		
		switch (tipoConfirmacao) {
			case INSERIR:
				System.out.println("Inserir");
				break;
			case ALTERAR:
				System.out.println("Alterar");
				break;
			default:
				System.out.println("Remover");
				break;
		}
	}
	
	/**
	 * Cancela a ação em andamento
	 */
	public void cancelar() {
		setConfirmando(false);
	}
	
	
	/**
	 * Definir o estado dos botões de acordo com a situação de confirmação atual
	 */
	public void atualizarEstados() {
		/* Se estiver confirmando uma ação, bloquear qualquer tentativa de realizar uma nova ação */
		if (isConfirmando()) {
			botoes.get("Inserir").setEnabled(false);
			botoes.get("Remover").setEnabled(false);
			botoes.get("Alterar").setEnabled(false);
			botoes.get("Confirmar").setEnabled(true);
			botoes.get("Cancelar").setEnabled(true);
		}
		/* Senão, bloquear qualquer tentativa de confirmação ou cancelamento devido à não existência de ações */
		else {
			botoes.get("Inserir").setEnabled(true);
			botoes.get("Remover").setEnabled(true);
			botoes.get("Alterar").setEnabled(true);
			botoes.get("Confirmar").setEnabled(false);
			botoes.get("Cancelar").setEnabled(false);
		}
	}
	
	public boolean isConfirmando() {
		return confirmando.get();
	}
	
	private void setConfirmando(boolean confirmando) {
		this.confirmando.set(confirmando);
	}
	
	public BooleanProperty confirmandoProperty() {
		return confirmando;
	}
	
	public TipoConfirmacao getTipoConfirmacao() {
		return tipoConfirmacao;
	}
	
	/**
	 * Enum com os tipos de confirmação
	 */
	private enum TipoConfirmacao {
		INSERIR, REMOVER, ALTERAR
	}
}
