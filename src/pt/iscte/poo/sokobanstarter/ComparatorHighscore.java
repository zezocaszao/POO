package pt.iscte.poo.sokobanstarter;

import java.util.Comparator;


public class ComparatorHighscore implements Comparator<Score>{

    @Override
    public int compare(Score o1, Score o2) {
        return o1.getScored()-o2.getScored();
    }

}