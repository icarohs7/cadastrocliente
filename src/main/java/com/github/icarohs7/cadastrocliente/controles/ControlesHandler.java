package com.github.icarohs7.cadastrocliente.controles;

import com.github.icarohs7.cadastrocliente.formulario.FormularioController;
import com.github.icarohs7.cadastrocliente.model.Cliente;
import com.github.icarohs7.cadastrocliente.model.ClienteDAO;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;
import javax.swing.JButton;

/**
 * Classe utilizada para tratar o comportamento dos botões
 */
@SuppressWarnings("deprecation")
class ControlesHandler extends Observable {
	private ClienteDAO dao = ClienteDAO.getINSTANCE();
	/**
	 * Runnable a ser executado em caso de confirmação
	 */
	private Runnable confirmavel = null;
	
	ControlesHandler(Map<String, JButton> botoes) {
		botoes.get("inserir").addActionListener(this::inserir);
		botoes.get("remover").addActionListener(this::remover);
		botoes.get("alterar").addActionListener(this::alterar);
		botoes.get("confirmar").addActionListener(this::confirmar);
		botoes.get("cancelar").addActionListener(this::cancelar);
		botoes.get("sair").addActionListener(this::sair);
		
		/* Observer reagindo a eventos de mudanças no objeto atual */
		Observer atualizar = (observable, o) -> botoes.forEach(this::atualizarBotao);
		addObserver(atualizar);
		FormularioController.getInstance().addObserver((o, arg) -> {
			confirmavel = null;
			liberarCampos(false);
			atualizar.update(o, arg);
		});
		atualizar.update(null, null);
	}
	
	private void inserir(ActionEvent evt) {
		FormularioController.getInstance().limparCliente();
		liberarCampos(true);
		setConfirmavel(() -> dao.inserir(FormularioController.getInstance().getCliente()));
	}
	
	private void remover(ActionEvent evt) {
		liberarCampos(false);
		setConfirmavel(() -> dao.remover(FormularioController.getInstance().getCliente()));
	}
	
	private void alterar(ActionEvent evt) {
		liberarCampos(true);
		setConfirmavel(() -> dao.alterar(FormularioController.getInstance().getCliente()));
	}
	
	private void confirmar(ActionEvent evt) {
		liberarCampos(false);
		confirmavel.run();
		FormularioController.getInstance().limparCliente();
		setConfirmavel(null);
	}
	
	private void cancelar(ActionEvent evt) {
		liberarCampos(false);
		FormularioController.getInstance().limparCliente();
		setConfirmavel(null);
	}
	
	private void sair(ActionEvent evt) { System.exit(0); }
	
	/**
	 * Atualiza os estados de ativação dos botões
	 */
	private void atualizarBotao(String nome, JButton button) {
		/* Somente ativados quando confirmando */
		if (nome.equals("confirmar") || nome.equals("cancelar")) {
			button.setEnabled(isConfirmando());
		}
		/* Somente ativados quando não confirmando */
		else {
			/* Somente ativados quando o cliente sendo editado no formulário existir */
			if (nome.equals("alterar") || nome.equals("remover")) {
				var ids = dao.getClientes().stream().map(Cliente::getId).collect(Collectors.toList());
				if (ids.contains(FormularioController.getInstance().getCliente().getId())) {
					button.setEnabled(!isConfirmando());
				} else {
					button.setEnabled(false);
				}
			}
			/* Quando não correspondente às outras condições, ativar somente quando não confirmado */
			else {
				button.setEnabled(!isConfirmando());
			}
		}
	}
	
	/**
	 * Alterar estado de ativação dos campos do formulário
	 * @param isLiberado Se os campos estarão ativos ou inativos
	 */
	private void liberarCampos(boolean isLiberado) { FormularioController.getInstance().setEnabled(isLiberado); }
	
	private boolean isConfirmando() { return confirmavel != null; }
	
	public void setConfirmavel(Runnable confirmavel) {
		this.confirmavel = confirmavel;
		setChanged();
		notifyObservers();
	}
}
