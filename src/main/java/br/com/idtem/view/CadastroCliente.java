package br.com.idtem.view;

import net.miginfocom.swing.MigLayout;

import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CadastroCliente extends JFrame {
	private static final int LARGURA_PADRAO = 800;
	private static final int ALTURA_PADRAO = 600;
	
	public CadastroCliente() throws HeadlessException {
		/* Definir título e operação de fechamento */
		super("Cadastro de Cliente");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		/* Definir painel raiz e suas constraints */
		var root = new JPanel(new MigLayout("flowy", "[fill,grow]"));
		setContentPane(root);
		
		/* Adicionar subpaineis à tela */
		root.add(new PainelCampos());
		root.add(new PainelBotoes());
		root.add(new PainelTabela(), "h 100%");
		
		/* Dimensionar o frame e reposiciona-lo */
		setSize(LARGURA_PADRAO, ALTURA_PADRAO);
		setLocationRelativeTo(null);
	}
}
