package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
public abstract class Element implements ImageTile {
	private Point2D coordenadas;
	
	public Element(Point2D coordenadas) {
		this.coordenadas=coordenadas;
	}
	@Override
	public Point2D getPosition() {
		return coordenadas;
	}
	public void setPosition(Point2D newCoordenadas) {
		coordenadas = newCoordenadas;
	}


	



}
