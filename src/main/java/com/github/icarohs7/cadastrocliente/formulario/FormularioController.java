package com.github.icarohs7.cadastrocliente.formulario;

import com.github.icarohs7.cadastrocliente.model.Cliente;
import java.util.Observable;

/**
 * Controller do formulário
 */
@SuppressWarnings("deprecation")
public class FormularioController extends Observable {
	/**
	 * View associada ao controller
	 */
	private FormularioView view;
	/**
	 * Cliente associado ao formulário
	 */
	private Cliente cliente;
	
	private FormularioController() {
		cliente = new Cliente();
		limparCliente();
		view = new FormularioView(cliente);
	}
	
	public FormularioView getView() { return view; }
	
	public Cliente getCliente() { return cliente; }
	
	public void setCliente(Cliente cliente) {
		this.cliente.setId(cliente.getId());
		this.cliente.setNome(cliente.getNome());
		this.cliente.setTelResidencial(cliente.getTelResidencial());
		this.cliente.setTelComercial(cliente.getTelComercial());
		this.cliente.setTelCelular(cliente.getTelCelular());
		this.cliente.setEmail(cliente.getEmail());
		setChanged();
		notifyObservers();
	}
	
	public boolean isEnabled() {
		return view.isEnabled();
	}
	
	public void setEnabled(boolean enabled) {
		view.setEnabled(enabled);
	}
	
	public void limparCliente() {
		setChanged();
		notifyObservers();
		cliente.limpar();
	}
	
	private static final FormularioController INSTANCE;
	
	static { INSTANCE = new FormularioController(); }
	
	public static FormularioController getInstance() { return INSTANCE; }
}
