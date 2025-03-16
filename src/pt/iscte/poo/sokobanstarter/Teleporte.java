package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Teleporte extends Element implements Linkable {

	public Teleporte(Point2D Point2D) {
		super(Point2D);
	}

	@Override
	public String getName() {
		return "Teleporte";
	}

	@Override
	public Point2D getPosition() {
		return super.getPosition();
	}

	@Override
	public int getLayer() {
		return 0;
	}

	public boolean isTeleportable(Element a) {
		// Verifica se dado um teleporte, o outro teleporte não possui GameElements do
		// tipo Caixote ou Palete,
		// ou seja, se o Teleport é utilizável
		List<Element> lista = new ArrayList<>(GameEngine.getInstance().getElementList());
		if (a instanceof Teleporte) {
			for (Element b : lista) {
				List<Element> elementsAtPositionB = GameEngine.getInstance().getElementsAtPosition(b.getPosition());
				if (b instanceof Teleporte && !b.equals(a)) {
					for (Element n : elementsAtPositionB) {
						if (n instanceof Caixote || n instanceof Palete) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	public void link(Point2D p) {
		//Teleporta.
		
		List<Element> lista = new ArrayList<>(GameEngine.getInstance().getElementList());
		List<Element> elementsAtPosition = GameEngine.getInstance().getElementsAtPosition(p);
		
		for (Element m : lista) {
			for (Element n : elementsAtPosition) {
				if (m instanceof Teleporte && !m.getPosition().equals(p)) {
					if (n instanceof Empilhadora || n instanceof Caixote || n instanceof Palete) {
						n.setPosition(m.getPosition());
					}
				}
			}
		}
	}

}
