package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Caixote extends Element implements Movable, Linkable {
	private boolean erased = false;

	public Caixote(Point2D Point2D) {
		super(Point2D);
	}

	@Override
	public String getName() {
		return "Caixote";
	}

	@Override
	public Point2D getPosition() {
		return super.getPosition();
	}

	@Override
	public int getLayer() {
		return 2;
	}

	public boolean wasErased() {
		return erased;
	}

	public void move(int Key) {
		// Muda o Caixote de posição consoante for possível e consoante a chamada do
		// movimento da Empilhadora
		// (Método "move" da Empilhadora)
		Direction direction = Direction.directionFor(Key);
		Empilhadora bobcat = GameEngine.getInstance().getBobcat();
		List<Element> lista = GameEngine.getInstance().getElementList();
		List<Element> elementsAtPosition = GameEngine.getInstance().getElementsAtPosition(getPosition());
		for (Element a : elementsAtPosition) {

			if (a instanceof Caixote && a.getPosition().equals(bobcat.getPosition())) {
				Point2D newCaixotePosition = a.getPosition().plus(direction.asVector());
				if (isMovable(newCaixotePosition, Key)) {
					a.setPosition(newCaixotePosition);
					link(a.getPosition());
				}
			}
			for (Element b : elementsAtPosition) {
				for (Element c : lista) {
					if (a instanceof Caixote && b instanceof Teleporte && c instanceof Teleporte
							&& !c.getPosition().equals(b.getPosition())) {
						Point2D newCaixotePosition = a.getPosition().plus(direction.asVector());
						if (isMovable(newCaixotePosition, Key)) {
							a.setPosition(newCaixotePosition);
							link(a.getPosition());
						}
					}
				}
			}

		}

	}

	public boolean isMovable(Point2D a, int Key) {
		// Define se é possível o Caixote se mexer para um dado ponto de acordo com as
		// circunstâncias
		List<Element> elementsAtPosition = GameEngine.getInstance().getElementsAtPosition(a);
		
		for (Element c : elementsAtPosition) {
			if (c instanceof Parede) {
				return false;
			}
			if (c instanceof Caixote) {
				return false;
			}
			if (c instanceof Palete && !((Palete) c).hasBuraco(c.getPosition())) {
				return false;
			}
			if (c instanceof Empilhadora) {
				return false;
			}
			if (c instanceof Buraco && ((Buraco) c).hasDuasPaletes(a)) {
				return false;
			}
			if (c instanceof Buraco && ((Buraco) c).hasPalete(a)) {
				return true;
			}

		}
		return true;
	}

	@Override
	public void link(Point2D p) {
		// Define a ligação entre o Caixote e o Teleporte e o Buraco através de um cast,
		// no caso destes se encontrarem na mesma posição que o Caixote.
		List<Element> elementsAtPosition = GameEngine.getInstance().getElementsAtPosition(p);

		for (Element element : elementsAtPosition) {
			if (element instanceof Teleporte) {
				((Teleporte) element).link(p);
			} else if (element instanceof Buraco) {
				((Buraco) element).link(p);
				erased = true;
			}
		}
	}

}
