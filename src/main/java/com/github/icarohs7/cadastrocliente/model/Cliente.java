package com.github.icarohs7.cadastrocliente.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Bean utilizado para representar um cliente
 */
public class Cliente {
	private StringProperty id = new SimpleStringProperty("");
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty telResidencial = new SimpleStringProperty("");
	private StringProperty telComercial = new SimpleStringProperty("");
	private StringProperty telCelular = new SimpleStringProperty("");
	private StringProperty email = new SimpleStringProperty("");
	
	/**
	 * Limpar os dados
	 */
	public void limpar() {
		setId(String.valueOf(ClienteDAO.getINSTANCE().proximoId()));
		setNome("");
		setTelResidencial("");
		setTelComercial("");
		setTelCelular("");
		setEmail("");
	}
	
	public String getId() { return id.get(); }
	
	public void setId(String id) { this.id.set(id); }
	
	public StringProperty idProperty() { return id; }
	
	public String getNome() { return nome.get(); }
	
	public void setNome(String nome) { this.nome.set(nome); }
	
	public StringProperty nomeProperty() { return nome; }
	
	public String getTelResidencial() { return telResidencial.get(); }
	
	public void setTelResidencial(String telResidencial) { this.telResidencial.set(telResidencial); }
	
	public StringProperty telResidencialProperty() { return telResidencial; }
	
	public String getTelComercial() { return telComercial.get(); }
	
	public void setTelComercial(String telComercial) { this.telComercial.set(telComercial); }
	
	public StringProperty telComercialProperty() { return telComercial; }
	
	public String getTelCelular() { return telCelular.get(); }
	
	public void setTelCelular(String telCelular) { this.telCelular.set(telCelular); }
	
	public StringProperty telCelularProperty() { return telCelular; }
	
	public String getEmail() { return email.get(); }
	
	public void setEmail(String email) { this.email.set(email); }
	
	public StringProperty emailProperty() { return email; }
	
	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + nome.hashCode();
		result = 31 * result + telResidencial.hashCode();
		result = 31 * result + telComercial.hashCode();
		result = 31 * result + telCelular.hashCode();
		result = 31 * result + email.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		
		Cliente cliente = (Cliente) o;
		
		if (!id.equals(cliente.id)) { return false; }
		if (!nome.equals(cliente.nome)) { return false; }
		if (!telResidencial.equals(cliente.telResidencial)) { return false; }
		if (!telComercial.equals(cliente.telComercial)) { return false; }
		if (!telCelular.equals(cliente.telCelular)) { return false; }
		return email.equals(cliente.email);
	}
	
	@Override
	public String toString() {
		return "Cliente{" + "id=" + id + ", nome=" + nome + ", telResidencial=" + telResidencial + ", telComercial=" + telComercial + ", " + "telCelular=" + telCelular + ", email=" + email + '}';
	}
}
