package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;

public class Martelo extends Element implements Linkable {

	public Martelo(Point2D Coordenadas) {
		super(Coordenadas);
	}

	@Override
	public String getName() {
		return "Martelo";
	}

	@Override
	public Point2D getPosition() {
		return super.getPosition();
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public void link(Point2D p) {
		//Apaga-se a si próprio
		
		List<Element> lista = GameEngine.getInstance().getElementList();
		List<Element> removebat = new ArrayList<>();
		ImageMatrixGUI gui = GameEngine.getInstance().getGui();
		Empilhadora bobcat = GameEngine.getInstance().getBobcat();

		for (Element a : lista) {
			if (a instanceof Martelo && a.getPosition().equals(bobcat.getPosition())) {
				removebat.add(a);
				gui.removeImage(a);
			}

		}
		lista.removeAll(removebat);
	}

}
