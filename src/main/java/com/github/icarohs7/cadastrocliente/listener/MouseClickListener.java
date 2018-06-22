package com.github.icarohs7.cadastrocliente.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Interface funcional expondo somente o evento de clique
 * do mouse
 */
@FunctionalInterface
public interface MouseClickListener extends MouseListener {
	@Override
	default void mousePressed(MouseEvent e) { }
	@Override
	default void mouseReleased(MouseEvent e) { }
	@Override
	default void mouseEntered(MouseEvent e) { }
	@Override
	default void mouseExited(MouseEvent e) { }
}
