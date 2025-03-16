package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Alvo extends Element {

	public Alvo(Point2D Point2D) {
		super(Point2D);
	}

	@Override
	public String getName() {
		return "Alvo";
	}

	@Override
	public Point2D getPosition() {
		return super.getPosition();
	}

	@Override
	public int getLayer() {
		return 0;
	}

	public boolean hasCaixote() {
		// Assim como o nome indica, verifica a existência de um Caixote na posição do
		// Alvo.
		List<Element> elementsAtPosition = GameEngine.getInstance().getElementsAtPosition(getPosition());
		for (Element c : elementsAtPosition) {
			if (c instanceof Caixote && !(c instanceof Palete)) {
				return true;
			}
		}
		return false;
	}

	public int countAlvos() {
		//Conta os alvos que existem na lista
		List<Element> lista = GameEngine.getInstance().getElementList();
		int i = 0;
		for (Element a : lista)
			if (a instanceof Alvo) {
				i++;
			}
		return i;
	}

}