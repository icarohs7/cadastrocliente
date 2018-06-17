package br.com.idtem.controller;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import br.com.idtem.model.ClienteDAO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Controlador do painel de botões
 */
public class BotoesController {
	/**
	 * Instância do controller
	 */
	private static final BotoesController INSTANCE;
	
	static {
		INSTANCE = new BotoesController();
	}
	
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
	private final SaidaController saida = SaidaController.getINSTANCE();
	/**
	 * Tipo de confirmação
	 */
	public TipoConfirmacao tipoConfirmacao;
	/**
	 * Se há uma operação esperando confirmação no momento
	 */
	private BooleanProperty confirmando = new SimpleBooleanProperty(false);
	
	
	/**
	 * Inicialização do controller com a criação e configuração dos botões
	 */
	private BotoesController() {
		
		/* Criação dos botões */
		for (String nomeBotao: NOMES_BOTOES) {
			botoes.put(nomeBotao, new JButton(nomeBotao));
			botoes.get(nomeBotao).setPreferredSize(new Dimension(100, 30));
		}
		
		/* Adição dos listeners às ações dos botões */
		botoes.get("Inserir").addActionListener(evt -> inserir());
		botoes.get("Remover").addActionListener(evt -> remover());
		botoes.get("Alterar").addActionListener(evt -> alterar());
		botoes.get("Confirmar").addActionListener(evt -> confirmar());
		botoes.get("Cancelar").addActionListener(evt -> cancelar());
		botoes.get("Sair").addActionListener(evt -> System.exit(0));
		
		/* Registro de listeners extras */
		registrarListeners();
	}
	
	/**
	 * Inicializa o processo de inserção de um registro
	 */
	public void inserir() {
		/* Inserção só é permitida quando o cliente tem todos os seus
		 * dados preenchidos */
		if (saida.clienteExists(entrada.getCliente().getId())) {
			entrada.limparCliente();
			atualizarEstados();
		} else if (entrada.getCliente().isRegexValido()) {
			tipoConfirmacao = TipoConfirmacao.INSERIR;
			setConfirmando(true);
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
		}
	}
	
	/**
	 * Inicializa o processo de alteração de um registro
	 */
	public void alterar() {
		if (entrada.getCliente().isRegexValido() && saida.clienteExists(entrada.getCliente().getId())) {
			tipoConfirmacao = TipoConfirmacao.ALTERAR;
			setConfirmando(true);
		}
	}
	
	/**
	 * Confirma a ação em andamento
	 */
	public void confirmar() {
		setConfirmando(false);
		var cliente = entrada.getCliente();
		var dao = ClienteDAO.getINSTANCE();
		
		switch (tipoConfirmacao) {
			
			/* Confirmar o cadastro de um cliente */
			case INSERIR:
				if (dao.addCliente(cliente) > 1) {
					saida.sincronizarTabela();
					entrada.limparCliente();
					JOptionPane.showMessageDialog(null, "Cliente inserido com sucesso");
				} else {
					JOptionPane.showMessageDialog(null, "Ocorreu um erro ao inserir o cliente");
				}
				break;
			
			/* Confirmar a alteração de um cadastro */
			case ALTERAR:
				if (dao.alterCliente(cliente) > 0) {
					saida.sincronizarTabela();
					entrada.limparCliente();
					JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso");
				} else {
					JOptionPane.showMessageDialog(null, "Ocorreu um erro ao alterar o cliente");
				}
				break;
			
			/* Confirmar a remoção de um cadastro */
			default:
				if (dao.removeCliente(cliente) > 0) {
					saida.sincronizarTabela();
					entrada.limparCliente();
					JOptionPane.showMessageDialog(null, "Cliente removido com sucesso");
				} else {
					JOptionPane.showMessageDialog(null, "Ocorreu um erro ao remover o cliente");
				}
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
			if (saida.clienteExists(entrada.getCliente().getId())) {
				botoes.get("Remover").setEnabled(true);
				botoes.get("Alterar").setEnabled(true);
			} else {
				botoes.get("Remover").setEnabled(false);
				botoes.get("Alterar").setEnabled(false);
			}
			botoes.get("Confirmar").setEnabled(false);
			botoes.get("Cancelar").setEnabled(false);
		}
	}
	
	/**
	 * Registra os listeners para eventos
	 */
	private void registrarListeners() {
		confirmandoProperty().addListener((observable, oldValue, newValue) -> {
			atualizarEstados();
			entrada.setAllCamposEnabled(!newValue);
		});
	}
	
	public Map<String, JButton> getBotoes() {
		return botoes;
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
	public enum TipoConfirmacao {
		INSERIR, REMOVER, ALTERAR
	}
}
