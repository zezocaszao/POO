package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class ParedeRachada extends Element implements Linkable {


	public ParedeRachada(Point2D Point2D) {
		super(Point2D);
	}

	@Override
	public String getName() {
		return "ParedeRachada";
	}

	@Override
	public Point2D getPosition() {
		return super.getPosition();
	}

	@Override
	public int getLayer() {
		return 0;
	}

	@Override
	public void link(Point2D p) {
		//Apaga-se caso Empilhadora tenha o Martelo. 
		
		List<Element> lista = GameEngine.getInstance().getElementList();
		Empilhadora bobcat = GameEngine.getInstance().getBobcat();
		List<Element> removebat = new ArrayList<>();
		ImageMatrixGUI gui = GameEngine.getInstance().getGui();
		List<Element> elementsAtPosition = GameEngine.getInstance().getElementsAtPosition(p);

		if (bobcat.getMartelo()) {
			for (Element a : elementsAtPosition) {
				if (a instanceof ParedeRachada) {
					removebat.add(a);
					gui.removeImage(a);
				}
			}
			lista.removeAll(removebat);
		}
	}

}
