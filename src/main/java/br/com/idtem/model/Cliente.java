package br.com.idtem.model;

import javax.swing.JOptionPane;

import br.com.idtem.lib.validation.ClienteValidator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe representando um cliente
 */
@SuppressWarnings("deprecation")
public class Cliente {
	private StringProperty id = new SimpleStringProperty("" + ClienteDAO.getINSTANCE().getNextId());
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty telefoneResidencial = new SimpleStringProperty("");
	private StringProperty telefoneComercial = new SimpleStringProperty("");
	private StringProperty telefoneCelular = new SimpleStringProperty("");
	private StringProperty email = new SimpleStringProperty("");
	
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
	 * Verificar se o objeto contém todos os seus campos vazios
	 * @return Verdadeiro se o objeto está vazio e falso do contrário
	 */
	public boolean isVazio() {
		if (getNome().equals("")) {
			if (getTelefoneResidencial().equals("")) {
				if (getTelefoneComercial().equals("")) {
					if (getTelefoneCelular().equals("")) {
						return getEmail().equals("");
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Validar objeto em 2 níveis, primeiro verificando
	 * se suas propriedades estão preenchidas, em seguida
	 * verificando se as mesmas seguem os padrões definidos
	 * e caso a validação seja bem sucedida, formatar o nome
	 * preparando o cliente para o registro na base de dados
	 * @return Verdadeiro se a validação for bem sucedida ou falso
	 * 		case contrário
	 */
	public boolean isRegexValido() {
		if (isValido()) {
			return ClienteValidator.getINSTANCE().validate(this);
		} else {
			JOptionPane.showMessageDialog(null, "Preencha todos os dados do cliente primeiro");
			return false;
		}
	}
	
	/**
	 * Limpa o valor de todas as propriedades
	 */
	public void clear() {
		setId(ClienteDAO.getINSTANCE().getNextId());
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
	
	@Override
	public String toString() {
		return "Cliente{" +
		       "id=" + getId() +
		       ", nome=" + getNome() +
		       ", telefoneResidencial=" + getTelefoneResidencial() +
		       ", telefoneComercial=" + getTelefoneComercial() +
		       ", telefoneCelular=" + getTelefoneCelular() +
		       ", email=" + getEmail() +
		       '}';
	}
}
