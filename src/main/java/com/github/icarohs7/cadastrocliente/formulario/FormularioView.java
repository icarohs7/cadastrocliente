package com.github.icarohs7.cadastrocliente.formulario;

import com.github.icarohs7.cadastrocliente.model.Cliente;
import com.github.icarohs7.unoxlib.swing.controls.NXField;
import java.util.Map;
import java.util.Map.Entry;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 * View contendo o formulário
 */
class FormularioView extends JPanel {
	/**
	 * Se os campos estão ativados ou desativados
	 */
	private BooleanProperty enabled = new SimpleBooleanProperty(true);
	/**
	 * Cliente atrelado aos campos
	 */
	private Cliente cliente;
	/**
	 * Mapa de campos
	 */
	private Map<String, NXField> campos = Map.of(
			"id", new NXField("ID:"),
			"nome", new NXField("Nome:"),
			"telResidencial", new NXField("Tel. Residencial:"),
			"telComercial", new NXField("Tel. Comercial:"),
			"telCelular", new NXField("Tel. Celular:"),
			"email", new NXField("E-mail:"));
	
	/**
	 * Compor o formulário
	 * @param cliente Cliente atrelado aos campos
	 */
	FormularioView(Cliente cliente) {
		super(new MigLayout("nogrid"));
		campos.get("id").setEnabled(false);
		
		/* Definição do cliente */
		setCliente(cliente);
		
		/* Construção da interface */
		add(campos.get("id"), "w 10%");
		add(campos.get("nome"), "w 90%, wrap");
		add(campos.get("telResidencial"), "w 100%");
		add(campos.get("telComercial"), "w 100%");
		add(campos.get("telCelular"), "w 100%, wrap");
		add(campos.get("email"), "w 100%");
		
		/* Realizar o binding da propriedade enabled com a propriedade homônima presente nos campos */
		enabledProperty().addListener((observable, oldValue, newValue) -> {
			campos.entrySet()
			      .stream()
			      .filter(field -> !field.getKey().equals("id"))
			      .map(Entry::getValue)
			      .forEach(field -> field.setEnabled(newValue));
		});
		setEnabled(false);
	}
	
	/**
	 * Realizar o binding dos campos com o cliente
	 */
	private void bind() {
		campos.get("id")
		      .textProperty()
		      .bindBidirectional(cliente.idProperty());
		campos.get("nome")
		      .textProperty()
		      .bindBidirectional(cliente.nomeProperty());
		campos.get("telResidencial")
		      .textProperty()
		      .bindBidirectional(cliente.telResidencialProperty());
		campos.get("telComercial")
		      .textProperty()
		      .bindBidirectional(cliente.telComercialProperty());
		campos.get("telCelular")
		      .textProperty()
		      .bindBidirectional(cliente.telCelularProperty());
		campos.get("email")
		      .textProperty()
		      .bindBidirectional(cliente.emailProperty());
	}
	
	Cliente getCliente() { return cliente; }
	
	void setCliente(Cliente cliente) {
		this.cliente = cliente;
		bind();
	}
	
	@Override
	public boolean isEnabled() { return enabled.get(); }
	
	@Override
	public void setEnabled(boolean enabled) { this.enabled.set(enabled); }
	
	public BooleanProperty enabledProperty() { return enabled; }
}
