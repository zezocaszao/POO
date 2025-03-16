package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Bateria extends Element implements Linkable {

	private int charge = 50; //carga da bateria

	public void setCharge(int i) {
		charge = i;
	}

	public int getCharge() {
		return charge;
	}

	public Bateria(Point2D Point2D) {
		super(Point2D);
	}

	@Override
	public String getName() {
		return "Bateria";
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
		//Apaga-se a si própria
		
		List<Element> lista = GameEngine.getInstance().getElementList();
		List<Element> removebat = new ArrayList<>();
		ImageMatrixGUI gui = GameEngine.getInstance().getGui();
		
		List<Element> elementsAtPosition = GameEngine.getInstance().getElementsAtPosition(p);

		for (Element a : elementsAtPosition)
			if (a instanceof Bateria) {
				removebat.add(a);
				gui.removeImage(a);
			}
		lista.removeAll(removebat);
		elementsAtPosition.removeAll(removebat);

	}

}