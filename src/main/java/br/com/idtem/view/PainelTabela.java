package br.com.idtem.view;

import com.github.icarohs7.unoxlib.tables.ScrollTable;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;

import br.com.idtem.controller.SaidaController;

/**
 * Painel contendo a tabela de clientes cadastrados
 */
class PainelTabela extends JPanel {
	private SaidaController controller = SaidaController.getInstance();
	
	/**
	 * Inicialização do painel
	 */
	PainelTabela() {
		super(new MigLayout("nogrid"));
		criarComponentes();
	}
	
	/**
	 * Criar componentes e posiciona-los no painel
	 */
	private void criarComponentes() {
		/* Label clientes cadastrados e seu separador */
		var label = new JLabel("Clientes cadastrados");
		label.setForeground(new Color(0x0044ff));
		var separator = new JSeparator();
		separator.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0x0044ff)));
		
		/* Definições da tabela */
		var table = ScrollTable.ofACustomModel(controller.getTabela());
		var scrollTable = table.getScrollableTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		table.getColumnModel().getColumn(0).setMinWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		
		/* Montagem do painel */
		add(label);
		add(separator, "w 100%, wrap");
		add(scrollTable, "w 100%, h 100%");
		
		/* Registrar listener para cliques na tabela */
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/* Se for um clique duplo */
				if (e.getClickCount() % 2 == 0 && !e.isConsumed()) {
					e.consume();
					int row = table.rowAtPoint(e.getPoint());
					controller.editarCliente(row);
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) { }
			
			@Override
			public void mouseReleased(MouseEvent e) { }
			
			@Override
			public void mouseEntered(MouseEvent e) { }
			
			@Override
			public void mouseExited(MouseEvent e) { }
		});
	}
}
