package com.github.icarohs7;

import com.github.icarohs7.cadastrocliente.controles.ControlesController;
import com.github.icarohs7.cadastrocliente.formulario.FormularioController;
import com.github.icarohs7.cadastrocliente.tabela.TabelaController;
import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.miginfocom.swing.MigLayout;

/**
 * Classe de entrada da aplicação
 */
public class CadastroCliente {
	/**
	 * Método contendo a Thread principal da aplicação
	 * @param args: Argumentos da linha de comando
	 */
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(new AluminiumLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		var frame = new JFrame("Cadastro de Clientes");
		frame.setContentPane(new JPanel(new MigLayout("flowy, nogrid", "[fill,grow]")));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		var root = frame.getContentPane();
		root.add(FormularioController.getInstance().getView());
		root.add(ControlesController.getInstance().getView());
		root.add(TabelaController.getInstance().getView(), "h 100%");
		
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
