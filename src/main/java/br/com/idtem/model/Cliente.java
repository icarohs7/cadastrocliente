package br.com.idtem.model;

import java.util.Observable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe representando um cliente
 */
@SuppressWarnings("deprecation")
public class Cliente extends Observable {
	private StringProperty id = new SimpleStringProperty("0");
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty telefoneResidencial = new SimpleStringProperty("");
	private StringProperty telefoneComercial = new SimpleStringProperty("");
	private StringProperty telefoneCelular = new SimpleStringProperty("");
	private StringProperty email = new SimpleStringProperty("");
	
	/**
	 * Construtor padrão registrando os listeners para eventos
	 */
	public Cliente() {
		
		/* Rotina para disparar o evento de mudança aos observers */
		Runnable mudar = () -> {
			setChanged();
			notifyObservers();
		};
		
		/* Ligar as mudanças dos campos individuais ao observable da classe */
		idProperty().addListener((observable, oldValue, newValue) -> mudar.run());
		nomeProperty().addListener((observable, oldValue, newValue) -> mudar.run());
		telefoneResidencialProperty().addListener((observable, oldValue, newValue) -> mudar.run());
		telefoneComercialProperty().addListener((observable, oldValue, newValue) -> mudar.run());
		telefoneCelularProperty().addListener((observable, oldValue, newValue) -> mudar.run());
		emailProperty().addListener((observable, oldValue, newValue) -> mudar.run());
	}
	
	/**
	 * Verificar se o objeto é válido, com todas as propriedades definidas
	 * @return true se o objeto for válido e false se não
	 */
	public boolean isValido() {
		if (getId() != 0) {
			if (!getNome().equals("")) {
				if (!getTelefoneResidencial().equals("")) {
					if (!getTelefoneComercial().equals("")) {
						if (!getTelefoneCelular().equals("")) {
							return !getEmail().equals("");
						}
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Limpa o valor de todas as propriedades
	 */
	public void clear() {
		setId(0);
		setNome("");
		setTelefoneResidencial("");
		setTelefoneComercial("");
		setTelefoneCelular("");
		setEmail("");
	}
	
	/**
	 * Define o valor dar propriedades para os valores presentes
	 * no cliente passado
	 * @param cliente Cliente a ser copiado
	 */
	public void setAll(Cliente cliente) {
		setId(cliente.getId());
		setNome(cliente.getNome());
		setTelefoneResidencial(cliente.getTelefoneResidencial());
		setTelefoneComercial(cliente.getTelefoneComercial());
		setTelefoneCelular(cliente.getTelefoneCelular());
		setEmail(cliente.getEmail());
	}
	
	public int getId() {
		return Integer.parseInt(id.get());
	}
	
	public void setId(int id) {
		this.id.set(String.valueOf(id));
	}
	
	public StringProperty idProperty() {
		return id;
	}
	
	public String getNome() {
		return nome.get();
	}
	
	public void setNome(String nome) {
		this.nome.set(nome);
	}
	
	public StringProperty nomeProperty() {
		return nome;
	}
	
	public String getTelefoneResidencial() {
		return telefoneResidencial.get();
	}
	
	public void setTelefoneResidencial(String telefoneResidencial) {
		this.telefoneResidencial.set(telefoneResidencial);
	}
	
	public StringProperty telefoneResidencialProperty() {
		return telefoneResidencial;
	}
	
	public String getTelefoneComercial() {
		return telefoneComercial.get();
	}
	
	public void setTelefoneComercial(String telefoneComercial) {
		this.telefoneComercial.set(telefoneComercial);
	}
	
	public StringProperty telefoneComercialProperty() {
		return telefoneComercial;
	}
	
	public String getTelefoneCelular() {
		return telefoneCelular.get();
	}
	
	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular.set(telefoneCelular);
	}
	
	public StringProperty telefoneCelularProperty() {
		return telefoneCelular;
	}
	
	public String getEmail() {
		return email.get();
	}
	
	public void setEmail(String email) {
		this.email.set(email);
	}
	
	public StringProperty emailProperty() {
		return email;
	}
}
