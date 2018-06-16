package br.com.idtem;

import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.com.idtem.view.CadastroCliente;

/**
 * Classe de entrada da aplicação
 */
public class Aplicacao {
	/**
	 * Método contendo a Thread principal da aplicação
	 * @param args: Argumentos da linha de comando
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new GraphiteLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Look&Feel Não Suportado");
		}
		
		new CadastroCliente().setVisible(true);
	}
}
