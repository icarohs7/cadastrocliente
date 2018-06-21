package br.com.idtem.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

public class ClienteDAO {
	private static ClienteDAO INSTANCE = new ClienteDAO();
	
	public static ClienteDAO getINSTANCE() {
		return INSTANCE;
	}
	
	/**
	 * Retorna a lista de clientes cadastrados
	 * @return Lista de clientes
	 */
	public List<Cliente> getClientes() {
		return onDbContext(db -> {
			var sql = "SELECT * FROM cliente";
			try (var stmt = db.createStatement()) {
				var lista = new LinkedList<Cliente>();
				var rs = stmt.executeQuery(sql);
				while (rs.next()) {
					var cliente = new Cliente();
					cliente.setId(rs.getInt("id"));
					cliente.setNome(rs.getString("nome"));
					cliente.setTelefoneResidencial(rs.getString("telresidencial"));
					cliente.setTelefoneComercial(rs.getString("telcomercial"));
					cliente.setTelefoneCelular(rs.getString("telcelular"));
					cliente.setEmail(rs.getString("email"));
					lista.add(cliente);
				}
				return lista;
			}
		});
	}
	
	/**
	 * Cadastra um cliente
	 * @param cliente Cliente a ser cadastrado
	 * @return Número de linhas afetadas
	 */
	public int addCliente(Cliente cliente) {
		var sql = "INSERT INTO cliente (nome,telresidencial,telcomercial,telcelular,email) VALUES (?,?,?,?,?)";
		return runDml(cliente, sql);
	}
	
	/**
	 * Altera as informações de um cliente existente no banco de dados
	 * @param cliente Cliente a ser alterado
	 * @return O número de linhas afetadas
	 */
	public int alterCliente(Cliente cliente) {
		var sql = "UPDATE " +
		          "cliente " +
		          "SET nome=?, telresidencial=?, telcomercial=?, telcelular=?, email=? " +
		          "WHERE id =" + cliente.getId();
		return runDml(cliente, sql);
	}
	
	/**
	 * Remove um cliente do banco
	 * @param cliente Cliente a ser excluído
	 * @return O número de linhas afetadas
	 */
	public int removeCliente(Cliente cliente) {
		var sql = "DELETE FROM cliente WHERE id=" + cliente.getId();
		var res = onDbContext(db -> {try (var stmt = db.createStatement()) { return stmt.executeUpdate(sql);}});
		return safeReturn(res, 0);
	}
	
	/**
	 * Remove um cliente do banco
	 * @param id ID do cliente a ser excluído
	 * @return O número de linhas afetadas
	 */
	public int removeCliente(int id) {
		var cliente = new Cliente();
		cliente.setId(id);
		return removeCliente(cliente);
	}
	
	/**
	 * Retorna o próximo id a set utilizado
	 * @return O próximo id
	 */
	public int getNextId() {
		var ret = onDbContext(db -> {
			var sql = "SHOW TABLE STATUS WHERE `Name` = 'cliente'";
			try (var stmt = db.createStatement()) {
				var rs = stmt.executeQuery(sql);
				rs.next();
				return rs.getInt("Auto_increment");
			}
		});
		
		if (ret == null) {
			return 1;
		} else {
			return ret;
		}
	}
	
	/**
	 * Função auxiliar para execução de statements dml relacionado ao cliente
	 * @param cliente Cliente
	 * @param sql     Statement sql
	 * @return Número de linhas afetadas
	 */
	private Integer runDml(Cliente cliente, String sql) {
		var res = onDbContext(db -> {
			try (var stmt = db.prepareStatement(sql)) {
				stmt.setString(1, cliente.getNome());
				stmt.setString(2, cliente.getTelefoneResidencial());
				stmt.setString(3, cliente.getTelefoneComercial());
				stmt.setString(4, cliente.getTelefoneCelular());
				stmt.setString(5, cliente.getEmail());
				
				return stmt.executeUpdate();
			}
		});
		
		return safeReturn(res, 0);
	}
	
	/**
	 * Método auxiliar para evitar NPEs (Null Pointer Exceptions)
	 * @param num      Valor processado
	 * @param fallback Valor retornado caso o primeiro seja falso
	 * @return O número ou 0, caso o mesmo seja nulo
	 */
	private <T> T safeReturn(@Nullable T num, @NotNull T fallback) {
		if (num != null) {
			return num;
		} else {
			return fallback;
		}
	}
	
	/**
	 * Abre uma nova conexão ao banco de dados
	 * @return A conexão (não esqueça de fechar)
	 */
	private Connection openConnection() throws SQLException {
		var url = "jdbc:mysql://localhost:3306/cadastrocliente?serverTimezone=UTC";
		return DriverManager.getConnection(url, "root", "0000");
	}
	
	/**
	 * Função para receber outra função executando dentro de um contexto
	 * de banco de dados de forma segura
	 * @param f   Função a ser executada
	 * @param <T> Tipo parametrizado do retorno
	 * @return Retorno desejado
	 */
	private <T> T onDbContext(DbRunnable<T> f) {
		try (var conn = openConnection()) {
			return f.run(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Houve um problema ao se conectar ao banco.");
			return null;
		}
	}
	
	/**
	 * Interface funcional para executar uma função sem retorno com acesso a
	 * uma conexão ao banco de dados
	 * @param <T> Tipo parametrizado do retorno
	 */
	@FunctionalInterface
	private interface DbRunnable<T> {
		/**
		 * Executa uma operação dentro de uma conexão ao banco de dados
		 * @param db Conexão
		 * @return O resultado da operação, definido pelo programador
		 * @throws SQLException Lança uma SQLExeption caso haja algum erro durante o uso do banco
		 */
		T run(Connection db) throws SQLException;
	}
}
