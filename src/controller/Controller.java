package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.Game;
import objects.Card;
import objects.Hand;

public class Controller {

	private Hand _hand;
	private BufferedWriter _writer;
	private Game game;
	private ArrayList<Hand> _hands;
	private int handsCount = 0;

	public Controller() {
	}

	public Controller(FileWriter archivoWriter) throws Exception {
		_writer = new BufferedWriter(archivoWriter);
		game = new Game();
		_hands = new ArrayList<>();

	}

	public void buildHand(String[] cards) throws IOException {
		_hand = new Hand(cards);
		_hand.addCards();
		_hands.add(_hand);
	}

	public void checkHand(ArrayList<String> input) throws IOException {
		
		
		for(int i = 0; i < _hands.size(); i++) {
			_writer.write(input.get(i));
			String bestHand = "  - Best hand: " + game.checkHand(_hands.get(i).getHand());
						
			_writer.newLine();
			_writer.write(bestHand);
			_writer.newLine();
		}
		
		_writer.close();
	}
	
	public void writeHands(List<String> playerHandsInfo) throws IOException {
		
	    _writer.newLine();

		for (String playerHandInfo : playerHandsInfo) {
		    _writer.write(playerHandInfo);
		    _writer.newLine();
		}
		
		_writer.close();
	}
	
	public String checkMultipleHands(String player) throws IOException {
		
		String bestHand = game.checkHand(_hand.getHand(), player);
	    
	    
	    return bestHand;
	}

}
