package com.github.icarohs7.cadastrocliente.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import javax.swing.JOptionPane;
import org.jdbi.v3.core.HandleCallback;
import org.jdbi.v3.core.Jdbi;

/**
 * API de acesso ao banco de dados
 */
@SuppressWarnings("deprecation")
public class ClienteDAO extends Observable {
	/**
	 * Instância do conector
	 */
	private final Jdbi JDBI = Jdbi.create(
			"jdbc:mysql://localhost:3306/cadastrocliente?serverTimezone=UTC", "root", "0000");
	/**
	 * Lista de clientes cacheada em memória
	 */
	private List<Cliente> clientes;
	
	private ClienteDAO() {
		addObserver((o, arg) -> {
			var temp = pesquisar();
			clientes.clear();
			clientes.addAll(temp);
		});
	}
	
	public boolean inserir(Cliente c) {
		var res = transaction(db -> db.createUpdate("INSERT INTO cliente(nome,telresidencial,telcomercial,telcelular,email) " +
		                                            "VALUES(:nome,:telResidencial,:telComercial,:telCelular,:email)")
		                              .bind("nome", c.getNome())
		                              .bind("telResidencial", c.getTelResidencial())
		                              .bind("telComercial", c.getTelComercial())
		                              .bind("telCelular", c.getTelCelular())
		                              .bind("email", c.getEmail())
		                              .execute())
				.map(numRows -> numRows > 0)
				.orElse(false);
		
		setChanged();
		notifyObservers();
		return res;
	}
	
	public List<Cliente> pesquisar() {
		return transaction(db -> db.createQuery("SELECT * FROM cliente").mapToBean(Cliente.class).list()).orElse(new LinkedList<>());
	}
	
	public boolean alterar(Cliente c) {
		var res = transaction(db -> db.createUpdate("UPDATE cliente SET " +
		                                            "nome = :nome," +
		                                            "telresidencial = :telResidencial," +
		                                            "telcomercial = :telComercial," +
		                                            "telcelular = :telCelular," +
		                                            "email = :email")
		                              .bind("nome", c.getNome())
		                              .bind("telResidencial", c.getTelResidencial())
		                              .bind("telComercial", c.getTelComercial())
		                              .bind("telCelular", c.getTelCelular())
		                              .bind("email", c.getEmail())
		                              .execute())
				.map(numRows -> numRows > 0)
				.orElse(false);
		
		setChanged();
		notifyObservers();
		return res;
	}
	
	public boolean remover(Cliente c) { return remover(Integer.parseInt(c.getId())); }
	
	public boolean remover(int id) {
		var res = transaction(db -> db.createUpdate("DELETE FROM cliente WHERE id=:id")
		                              .bind("id", id)
		                              .execute())
				.map(numRows -> numRows > 0)
				.orElse(false);
		
		setChanged();
		notifyObservers();
		return res;
	}
	
	/**
	 * Retorna o próximo id no auto incremento do banco
	 * @return Próximo ID a ser associado ao cliente
	 */
	public int proximoId() {
		return transaction(db ->
				Integer.parseInt(db.createQuery("SHOW TABLE STATUS WHERE `Name` = 'cliente'")
				                   .mapToMap()
				                   .findOnly()
				                   .get("auto_increment")
				                   .toString()))
				.orElse(1);
	}
	
	public List<Cliente> getClientes() {
		if (clientes == null) {
			clientes = pesquisar();
		}
		return clientes;
	}
	
	/**
	 * Abstração do acesso ao banco
	 * @param callback Função executada no contexto do banco de dados
	 * @param <T>      Tipo de retorno
	 * @return O retorno da função executada
	 */
	private <T> Optional<T> transaction(HandleCallback<T, Exception> callback) {
		try (var handle = JDBI.open()) {
			return Optional.of(callback.withHandle(handle));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao conectar ao banco de dados");
			return Optional.empty();
		}
	}
	
	private static ClienteDAO INSTANCE;
	
	static {INSTANCE = new ClienteDAO();}
	
	public static ClienteDAO getINSTANCE() { return INSTANCE; }
}
