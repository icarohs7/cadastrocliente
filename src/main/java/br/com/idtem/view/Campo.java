package br.com.idtem.view;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javafx.beans.property.StringProperty;

/**
 * Classe representando um campo de formulário
 * e sua label de nome
 */
public class Campo extends JPanel {
	private JLabel label;
	private JTextField campo;
	private String nomeDoCampo;
	
	public Campo(String nomeDoCampo) {
		super(new MigLayout("fillx, ins 2 2 2 2, gap 2 2 2 2"));
		this.nomeDoCampo = nomeDoCampo;
		
		/* Label para o campo de texto */
		label = new JLabel(nomeDoCampo + ":");
		
		/* Campo de texto */
		campo = new JTextField() {
			@Override
			public void setEnabled(boolean enabled) {
				super.setEnabled(enabled);
				if (enabled) {
					setBackground(Color.WHITE);
				} else {
					setBackground(Color.GRAY.brighter());
				}
			}
		};
		
		campo.addFocusListener(new FocusListener() {
			
			/* Remover placeholder ao focar o campo */
			@Override
			public void focusGained(FocusEvent e) {
				updatePlaceholder(true);
			}
			
			/* Adicionar placeholder ao desfocar o campo quando vazio */
			@Override
			public void focusLost(FocusEvent e) {
				updatePlaceholder(false);
			}
		});
		
		/* Mostrar placeholder na instanciação */
		campo.setForeground(Color.GRAY);
		campo.setText(nomeDoCampo);
		
		/* Adicionar componentes ao painel */
		add(label, "wrap");
		add(campo, "w 100%");
	}
	
	/**
	 * Realizar um binding bidirecional entre o texto do campo e a propriedade
	 * @param property Propriedade a ser atrelada ao campo
	 */
	public void bind(StringProperty property) {
		
		/* propriedade => campo */
		property.addListener((observable, oldValue, newValue) -> {
			if (!newValue.equals(campo.getText())) {
				campo.setText(newValue);
			}
		});
		
		/* campo => propriedade */
		campo.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				if (!campo.getText().equals(nomeDoCampo)) {
					property.set(campo.getText());
					if (campo.getText().length() > 0) {
						campo.setForeground(Color.BLACK);
					}
				}
			}
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				if (!campo.getText().equals(nomeDoCampo)) {
					property.set(campo.getText());
					if (campo.getText().length() > 0) {
						campo.setForeground(Color.BLACK);
					}
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) { }
		});
	}
	
	/**
	 * Atualiza o placeholder do campo para visível ou invisível de acordo com as condições
	 * @param focus Se o campo está em foco ou não
	 */
	private void updatePlaceholder(boolean focus) {
		if (!focus && campo.getText().length() < 1) {
			campo.setForeground(Color.GRAY);
			campo.setText(nomeDoCampo);
		} else if (campo.getForeground().equals(Color.GRAY)) {
			campo.setForeground(Color.BLACK);
			campo.setText("");
		}
	}
	
	public JLabel getLabel() {
		return label;
	}
	
	public JTextField getCampo() {
		return campo;
	}
}
