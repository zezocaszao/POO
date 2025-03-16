package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public interface Movable {
	public void move(int Key);//mexe
	public boolean isMovable(Point2D newBobcatPosition, int Key);//Booleano que define se é possível mexer.
}
