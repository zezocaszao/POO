package pt.iscte.poo.sokobanstarter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;

import javax.swing.JOptionPane;


import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	private static GameEngine INSTANCE; // Referencia para o unico objeto GameEngine (singleton)
	private ImageMatrixGUI gui; // Referencia para ImageMatrixGUI (janela de interface com o utilizador)
	private List<Element> ElementList; // Lista de GameElements
	private Empilhadora bobcat; // Referencia para a empilhadora
	private int nNivel = 0; // numero do nivel
	private String player;

	private GameEngine() {
		ElementList = new ArrayList<>();
	}

	public static GameEngine getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}

	public void start() {

		gui = ImageMatrixGUI.getInstance(); // 1. obter instancia ativa de ImageMatrixGUI
		gui.setSize(GRID_HEIGHT, GRID_WIDTH); // 2. configurar as dimensoes
		gui.registerObserver(this); // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go(); // 4. lancar a GUI

		player = gui.askUser("Insert your user name");
		// Criar o cenario de jogo
		createWarehouse();
		lerNiveis("levels/level" + nNivel + ".txt"); // criar o armazem
		// criar mais algun objetos (empilhadora, caixotes,...)
		sendImagesToGUI(); // enviar as imagens para a GUI

		// Escrever uma mensagem na StatusBar
		gui.setStatusMessage("Sokoban Starter - demo    Press R key to restart level ");
	}

	public List<Element> getElementList() {
		return ElementList;
	}

	public Empilhadora getBobcat() {
		return bobcat;
	}

	public boolean hasDuasCaixasNosAlvos() { // Declara se todas as caixas estão nos alvos(se o nível está completado)
		int i = 0;
		for (Element a : ElementList)
			if (a instanceof Alvo) {
				if (((Alvo) a).hasCaixote()) {
					i++;
				}
				if (i == ((Alvo) a).countAlvos())
					return true;
			}
		return false;
	}

	public List<Element> getElementsAtPosition(Point2D position) { // Retorna uma lista dos elementos numa dada
																		// posição
		List<Element> elementsAtPosition = new ArrayList<>();

		for (Element element : ElementList) {
			if (element.getPosition().equals(position)) {
				elementsAtPosition.add(element);
			}
		}

		return elementsAtPosition;
	}
	public boolean wasboxerased() {
		for(Element a: ElementList) {
			if (a instanceof Caixote) {
			if(((Caixote) a).wasErased()) {
				return true;
			}}
		}
		return false;
	}
	public int scoreifbateria() {
		if(bobcat.hasbateria()) {
			return 150 - bobcat.getBateria();
		}else {
			return 100 - bobcat.getBateria();
		}
	}
	

	public void removeLevel() { //o nome
		List<Element> removelevel = new ArrayList<>();

		for (Element a : ElementList) {
			removelevel.add(a);
		}
		gui.clearImages();
		ElementList.removeAll(removelevel);
	}



	
	public void restartgame() { // recomeca o jogo de inicio
		player = gui.askUser("Insert your user name");
		nNivel=0;
		createWarehouse();
		lerNiveis("levels/level" + nNivel + ".txt"); 
		
		sendImagesToGUI();
			
			
			
	}
	public void restartifrpressed(int key) {
		
		switch(key) {
		case KeyEvent.VK_R:
		restart();
			}
			
	}


	public void restart() { //recomeça o nível de onde perdeu 
		removeLevel();
		createWarehouse();
		lerNiveis("levels/level" + nNivel + ".txt");
		sendImagesToGUI();
	}

	@Override
	public void update(Observed source) {

		int key = gui.keyPressed();

		bobcat.move(key);
		 restartifrpressed(key);
		if (bobcat.getBateria() < 0 || bobcat.wasErased() || wasboxerased()) {// recomeça o nível se o tiver perdido
			gui.setMessage("Game Over");
			restart();
		}
	
		if (hasDuasCaixasNosAlvos()) {// se o nível for concluído
			Score score = new Score(nNivel, player, scoreifbateria());
			try {
				score.write();
				score.top3();
			} catch (IOException e) {
				e.printStackTrace();
			}
			gui.setMessage("Ganhaste o nivel " + nNivel +" com um score de: "+ scoreifbateria());
			removeLevel();
			createWarehouse();
			if(nNivel<6) {
			nNivel++;
			lerNiveis("levels/level" + nNivel + ".txt");
			sendImagesToGUI();
			}else {
				removeLevel();
				restartgame();
			}
		}

		gui.setStatusMessage("Nivel: "+ nNivel + "  Player: "+ player +"  Moves: "+ scoreifbateria() + "  Bateria: " + bobcat.getBateria());
		gui.update();

	}

	public void lerNiveis(String ficheiro) { //lê o nível

		try {
			Scanner s = new Scanner(new File(ficheiro));

			for (int y = 0; y < GRID_HEIGHT; y++)
				if (s.hasNextLine()) {
					String sigla = s.nextLine();

					for (int x = 0; x < GRID_HEIGHT; x++)
						switch (sigla.charAt(x)) {
						case ' ':
							ElementList.add(new Chao(new Point2D(x, y)));
							break;
						case '#':
							ElementList.add(new Parede(new Point2D(x, y)));
							break;
						case 'E':
							bobcat = new Empilhadora(new Point2D(x, y));
							ElementList.add(bobcat);
							break;
						case 'C':
							ElementList.add(new Caixote(new Point2D(x, y)));
							break;
						case '=':
							ElementList.add(new Vazio(new Point2D(x, y)));
							break;
						case 'B':
							ElementList.add(new Bateria(new Point2D(x, y)));
							break;
						case 'T':
							ElementList.add(new Teleporte(new Point2D(x, y)));
							break;
						case 'X':
							ElementList.add(new Alvo(new Point2D(x, y)));
							break;
						case '%':
							ElementList.add(new ParedeRachada(new Point2D(x, y)));
							break;
						case 'P':
							ElementList.add(new Palete(new Point2D(x, y)));
							break;
						case 'M':
							ElementList.add(new Martelo(new Point2D(x, y)));
							break;
						case 'O':
							ElementList.add(new Buraco(new Point2D(x, y)));
							break;
						}
				}

		} catch (

		FileNotFoundException e) {
			System.err.println("Ficheiro " + ficheiro + " nao encontrado");
		}
	}

	// Criacao da planta do armazem - so' chao neste exemplo
	private void createWarehouse() {

		for (int y = 0; y < GRID_HEIGHT; y++)
			for (int x = 0; x < GRID_HEIGHT; x++)
				ElementList.add(new Chao(new Point2D(x, y)));
	}

	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no
	// inicio
	// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes
	private void sendImagesToGUI() {
		for (ImageTile a : ElementList)
			gui.addImage(a);
	}

	public ImageMatrixGUI getGui() {
		return gui;
	}

}
