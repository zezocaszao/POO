package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;

public class Buraco extends Element implements Linkable {

	public Buraco(Point2D Coordenadas) {
		super(Coordenadas);
	}

	@Override
	public String getName() {
		return "Buraco";
	}

	@Override
	public Point2D getPosition() {
		return super.getPosition();
	}

	@Override
	public int getLayer() {
		return 0;
	}

	public boolean hasPalete(Point2D p) {
		//Assim como o nome indica, verifica a existência de uma Palete na mesma posição que o Buraco.
		List<Element> elementsAtPosition = GameEngine.getInstance().getElementsAtPosition(p);
		for (Element palete : elementsAtPosition) {
			if (palete instanceof Palete) {
				return true;
			}
		}
		return false;
	}

	public boolean hasDuasPaletes(Point2D p) {
		//Assim como o nome indica, verifica a existência de duas Paletes na mesma posição que o Buraco.
		List<Element> elementsAtPosition = GameEngine.getInstance().getElementsAtPosition(p);
		for (Element buraco : elementsAtPosition) {
			for (Element palete1 : elementsAtPosition) {
				for (Element palete2 : elementsAtPosition) {
					if (buraco instanceof Buraco && palete1 instanceof Palete && palete2 instanceof Palete
							&& !palete1.equals(palete2)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void link(Point2D p) {
		//Efetua a ligação entre o Buraco e a Empilhadora e o Caixote, 
		//removendo-os caso estejam na mesma posição
		List<Element> lista = new ArrayList<>(GameEngine.getInstance().getElementList());
		List<Element> elementsAtPosition = GameEngine.getInstance().getElementsAtPosition(p);
		List<Element> removebat = new ArrayList<>();
		ImageMatrixGUI gui = GameEngine.getInstance().getGui();

		for (Element a : elementsAtPosition) {

			if ((a instanceof Empilhadora || a instanceof Caixote) && !hasPalete(p)) {
				removebat.add(a);
				gui.removeImage(a);
			}
		}
		lista.removeAll(removebat);
		elementsAtPosition.removeAll(removebat);
	}
}
