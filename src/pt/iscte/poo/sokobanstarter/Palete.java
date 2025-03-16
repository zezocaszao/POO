package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Palete extends Caixote implements Movable, Linkable {

	public Palete(Point2D Coordenadas) {
		super(Coordenadas);
	}

	@Override
	public String getName() {
		return "Palete";
	}

	@Override
	public Point2D getPosition() {
		return super.getPosition();
	}

	@Override
	public int getLayer() {
		return 1;
	}

	public boolean hasBuraco(Point2D p) {
		// Verifica se existe um GameElement do tipo Buraco
		// na mesma posição que a Palete
		List<Element> elementsAtPosition = GameEngine.getInstance().getElementsAtPosition(p);
		for (Element buraco : elementsAtPosition) {
			if (buraco instanceof Buraco) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void link(Point2D p) {
		List<Element> elementsAtPosition = GameEngine.getInstance().getElementsAtPosition(p);
		List<Element> Elementlist = GameEngine.getInstance().getElementList();
		List<Element> removeElements = new ArrayList<>();
		
		//Se uma Palete e um Buraco estiverem na mesma posição, são automaticamente apagados 
		for (Element buraco : elementsAtPosition) {
			for (Element palete : elementsAtPosition) {
				if (buraco instanceof Buraco && palete instanceof Palete && !((Buraco) buraco).hasDuasPaletes(p)) {
					removeElements.add(palete);
					removeElements.add(buraco);
				}
			}
		}

		Elementlist.removeAll(removeElements);

		// Liga a Palete ao Teleporte caso estes estejam na mesma posição
		for (Element teleporte : elementsAtPosition) {
			if (teleporte instanceof Teleporte) {
				((Teleporte) teleporte).link(p);
			}
		}

	}
}
