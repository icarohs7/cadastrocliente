package br.com.idtem.lib.validation;

import javax.swing.JOptionPane;

import br.com.idtem.model.Cliente;

/**
 * Classe utilizada para validar e formatar os
 * dados de um cliente
 */
public class ClienteValidator {
	private static ClienteValidator INSTANCE;
	
	static {
		INSTANCE = new ClienteValidator();
	}
	
	public static ClienteValidator getINSTANCE() {
		return INSTANCE;
	}
	
	/**
	 * Regex de email
	 */
	private String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
	                            "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b" +
	                            "\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]" +
	                            "(?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
	                            "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:" +
	                            "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c" +
	                            "\\x0e-\\x7f])+)])";
	
	/**
	 * Regex de telefone
	 */
	private String phoneRegex = "^(\\(\\d\\d\\)\\s?)?\\d?\\d{4}-?\\d{4}$";
	
	/**
	 * Validar os dados do cliente
	 * @param cliente Cliente
	 * @return Verdadeiro se o cliente é válido
	 */
	public boolean validate(Cliente cliente) {
		if (cliente.getEmail().matches(emailRegex)) {
			if (cliente.getTelefoneResidencial().matches(phoneRegex)) {
				if (cliente.getTelefoneComercial().matches(phoneRegex)) {
					if (cliente.getTelefoneCelular().matches(phoneRegex)) {
						
						/* Formatar o nome do cliente com todas as letras minúsculas
						 * e somente a primeira letra de cada palavra maiúscula */
						var words = cliente.getNome().split("\\s");
						for (int i = 0; i < words.length; i++) {
							words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
						}
						cliente.setNome(String.join(" ", words));
						return true;
					} else {
						phoneRegexFail(2);  // Falha de telefone celular
					}
				} else {
					phoneRegexFail(1);  // Falha de telefone comercial
				}
			} else {
				phoneRegexFail(0);  // Falha de telefone residencial
			}
		} else {
			emailRegexFail();   // Falha de email
		}
		
		return false;
	}
	
	/**
	 * Mensagem de erro para email inválido
	 */
	private void emailRegexFail() {
		JOptionPane.showMessageDialog(null, "Emails devem seguir o padrao a@a.com, ex: jose@gmail.com");
	}
	
	/**
	 * Mensagem de telefone inválido
	 * @param type 0 => Residencial, 1 => Comercial, 2 => Celular
	 */
	private void phoneRegexFail(int type) {
		String msg;
		
		switch (type) {
			case 0:
				msg = "Telefone residencial";
				break;
			case 1:
				msg = "Telefone comercial";
				break;
			case 2:
				msg = "Telefone celular";
				break;
			default:
				throw new IllegalAccessError("Tipo inválido");
		}
		
		JOptionPane.showMessageDialog(null, msg + " deve conter no minimo 8 digitos, aceitando o nono digito " +
		                                    "e o DDD, ambos opcionalmente, ex: 12341234, 912341234, (31)12341234, (31) 912341234");
	}
}
