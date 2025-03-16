package pt.iscte.poo.sokobanstarter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Score {
	private String Player;
	private int scored;
	private int level;
	private List<Score> list;

	public Score(int level, String Player, int scored) {
		this.level = level;
		this.Player = Player;
		this.scored = scored;
		list = new ArrayList<Score>();
	}

	public String getPlayer() {
		return Player;
	}

	public void write() throws IOException {
		try {
			File creatfile = new File("scores/scores" + level + ".txt");
			PrintWriter fileWriter = new PrintWriter(new FileWriter(creatfile, true));
			fileWriter.println(Player + " " + scored);

			fileWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Error creating the file: " + e.getMessage());
		}

	}

	public List<Score> read() {
		Scanner s;
		try {
			s = new Scanner(new File("scores/scores" + level + ".txt"));
			while (s.hasNextLine()) {
				try {

					String a = s.nextLine();
					String[] splited = a.split(" ");
					String name = splited[0];
					int movements = Integer.parseInt(splited[1]);
					list.add(new Score(level, name, movements));
				} catch (NumberFormatException e) {

				}

			}

		} catch (FileNotFoundException e) {

		}
		list.sort(new ComparatorHighscore());
		return list;
	}

	public int getScored() {
		return scored;
	}

	public String toString() {
		return "HighScore player-" + Player + "-Score-" + scored;
	}

	public void top3() throws IOException {
		List<Score> scores = read();
		if (scores.isEmpty()) {
			System.out.println("Sem Scores Disponiveis");
			return;
		}
		File creatfile = new File("Top3/topScores" + level + ".txt");
		try (PrintWriter fileWriter = new PrintWriter(new FileWriter(creatfile, false));) {
			if (scores.size() > 3) {
				for (int i = 0; i < 3; i++) {
					System.out.println(scores.get(i));
					fileWriter.println(scores.get(i).toString());
				}
			} else {

				for (Score score : scores) {
					fileWriter.println(score.toString());
					System.out.println(score);
				}

			}
		} catch (IOException e) {
			System.out.println("Erro ao escrever: " + e.getMessage());
		}
	}

}
