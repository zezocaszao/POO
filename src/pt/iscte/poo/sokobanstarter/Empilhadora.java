package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Empilhadora extends Element implements Movable, Linkable {

	private int bateria = 100;
	private boolean hasbateria=false;
	private String imageName;
	private boolean hasMartelo; // Define se a Empilhadora possui ou não o Martelo
	private boolean erased = false;

	public Empilhadora(Point2D Coordenadas) {
		super(Coordenadas);
		imageName = "Empilhadora_D";
		hasMartelo = false;
	}

	public int getBateria() {
		return bateria;
	}
	public boolean hasbateria() {
		return hasbateria;
	}

	public void setBateria(int i) {
		this.bateria = i;
	}

	@Override
	public String getName() {
		return imageName;
	}

	@Override
	public Point2D getPosition() {

		return super.getPosition();
	}

	@Override
	public int getLayer() {
		return 2;
	}

	public boolean getMartelo() {
		return hasMartelo;
	}

	public void setMartelo(boolean martelo) {
		hasMartelo = martelo;
	}

	public boolean wasErased() {
		// Se foi apagado pelo Buraco
		return erased;
	}

	public void move(int Key) {
		// mexe a Empilhadora, e mexe Caixotes e Paletes caso se encontrem na mesma
		// posição e caso seja possível.

		Direction direction = Direction.directionFor(Key);
		if (Key == KeyEvent.VK_UP) {
			imageName = "Empilhadora_U";
		}
		if (Key == KeyEvent.VK_RIGHT) {
			imageName = "Empilhadora_R";
		}
		if (Key == KeyEvent.VK_LEFT) {
			imageName = "Empilhadora_L";
		}
		if (Key == KeyEvent.VK_DOWN) {
			imageName = "Empilhadora_D";
		}
		bateria--;

		Point2D newBobcatPosition = getPosition().plus(direction.asVector());

		if (newBobcatPosition.getX() >= 0 && newBobcatPosition.getX() < 10 && newBobcatPosition.getY() >= 0
				&& newBobcatPosition.getY() < 10 && isMovable(newBobcatPosition, Key)) {
			setPosition(newBobcatPosition);
		}

		List<Element> elementsAtPosition = GameEngine.getInstance().getElementsAtPosition(newBobcatPosition);

		for (Element e : elementsAtPosition) {
			if (e instanceof Caixote) {
				((Caixote) e).move(Key);
			}
			if (e instanceof Palete && !((Palete) e).hasBuraco(newBobcatPosition)) {
				((Palete) e).move(Key);
			}
			for (Element o : elementsAtPosition) {
				if (e instanceof Palete && o instanceof Buraco && ((Buraco) o).hasDuasPaletes(newBobcatPosition)) {
					((Palete) e).move(Key);
				}
			}
		}
		link(newBobcatPosition);
	}

	public boolean isMovable(Point2D newBobcatPosition, int Key) {
		// Define se é possível a Empilhadora mover-se para uma posição consoante as
		// diferentes circunstâncias

		Direction direction = Direction.directionFor(Key);

		List<Element> elementsAtPosition = GameEngine.getInstance().getElementsAtPosition(newBobcatPosition);
		List<Element> lista = GameEngine.getInstance().getElementList();
		List<Element> elementsAtPositionAhead = GameEngine.getInstance()
				.getElementsAtPosition(newBobcatPosition.plus(direction.asVector()));
		for (Element elements : elementsAtPosition) {

			if (elements instanceof Parede) {

				return false;
			}
			if (elements instanceof ParedeRachada && !getMartelo()) {
				return false;
			}
			for (Element elementsAhead : elementsAtPositionAhead) {

				if ((elements instanceof Caixote || elements instanceof Palete)
						&& (elementsAhead instanceof Parede || elementsAhead instanceof ParedeRachada)) {
					return false;
				}
				if ((elements instanceof Caixote || (elements instanceof Palete && !((Palete) elements).hasBuraco(elements.getPosition())))
						&& (elementsAhead instanceof Caixote
								|| (elementsAhead instanceof Palete && !((Palete) elementsAhead).hasBuraco(elementsAhead.getPosition())))) {
					return false;
				}

				for (Element otherE : lista) {
					if (elements instanceof Palete && elementsAhead instanceof Caixote && otherE instanceof Buraco
							&& !otherE.getPosition().equals(elements.getPosition())
							&& elements.getPosition().equals(elementsAhead.getPosition().plus(direction.asVector()))) {
						return false;
					}
					if (((elements instanceof Palete || elements instanceof Caixote) && elementsAhead instanceof Teleporte
							&& otherE instanceof Teleporte && !((Teleporte) otherE).isTeleportable(otherE))) {
						return false;
					}
					if (elements instanceof Teleporte && otherE instanceof Teleporte && !((Teleporte) otherE).isTeleportable(otherE)) {
						return false;
					}
				}
			}
		}
		return true;

	}

	@Override
	public void link(Point2D p) {
		// Efetua a ligação entre a Empilhadora e os vários GameElements que possam
		// estar presentes

		List<Element> elementsAtPosition = GameEngine.getInstance().getElementsAtPosition(p);

		for (Element element : elementsAtPosition) {
			if (element instanceof Bateria) {
				setBateria(getBateria() + 50);
				hasbateria=true;
				((Bateria) element).link(p);
			} else if (element instanceof Teleporte) {
				((Teleporte) element).link(p);
			} else if (element instanceof Martelo) {
				setMartelo(true);
				((Martelo) element).link(p);
			} else if (element instanceof ParedeRachada) {
				((ParedeRachada) element).link(p);

			}

			if (element instanceof Buraco && !((Buraco) element).hasPalete(p)) {
				((Buraco) element).link(p);
				erased = true;
			}
		}
	}

}