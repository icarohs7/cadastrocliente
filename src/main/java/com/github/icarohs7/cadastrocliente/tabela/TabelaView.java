package com.github.icarohs7.cadastrocliente.tabela;

import com.github.icarohs7.cadastrocliente.listener.MouseClickListener;
import com.github.icarohs7.cadastrocliente.listener.TableClickListener;
import com.github.icarohs7.unoxlib.swing.tables.NXTable;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import net.miginfocom.swing.MigLayout;

/**
 * View contendo a tabela
 */
class TabelaView extends JPanel {
	
	/**
	 * Compor a interface
	 * @param tabela Tabela mostrada
	 */
	TabelaView(JScrollPane tabela, TableClickListener listener) {
		super(new MigLayout("nogrid"));
		
		/* Label */
		var label = new JLabel("Clientes cadastrados");
		label.setForeground(new Color(0x2244ff));
		add(label);
		
		/* Separator */
		var separator = new JSeparator();
		separator.setForeground(new Color(0x2244ff));
		add(separator, "w 100%, wrap");
		
		/* Tabela */
		add(tabela, "w 100%, h 100%");
		var nxt = (NXTable) tabela.getViewport()
		                          .getView();
		nxt.getTableHeader()
		   .getColumnModel()
		   .getColumn(0)
		   .setMaxWidth(150);  // Tamanho máximo da coluna ID
		
		/* Listener para cliques na tabela */
		nxt.addMouseListener((MouseClickListener) e -> {
			var row = nxt.rowAtPoint(e.getPoint());
			var col = nxt.columnAtPoint(e.getPoint());
			listener.onCellClick(row, col);
		});
	}
}
