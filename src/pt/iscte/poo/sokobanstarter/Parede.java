package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
public class Parede extends Element {

		public Parede(Point2D Point2D){
			super(Point2D);
		}
		
		@Override
		public String getName() {
			return "Parede";
		}

		@Override
		public Point2D getPosition() {
			return super.getPosition();
		}

		@Override
		public int getLayer() {
			return 0;
		}

	}

