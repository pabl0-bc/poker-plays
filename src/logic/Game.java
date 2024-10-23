package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import objects.Card;
import objects.Hand;

public class Game {

	public static Map<String, String> dictionary;

	public Game() {
		dictionary = new HashMap<>();
		buildDiccionary();
	}

	private void buildDiccionary() {
		dictionary.put("h", "Hearts");
		dictionary.put("d", "Diamonds");
		dictionary.put("s", "Spades");
		dictionary.put("c", "Clubs");
	}

	public String checkDraws(ArrayList<Card> hand) {

		String ret = "";

		int cont = 1;
		int maxCont = 0;
		boolean gutShot = false;

		for (int i = 0; i < hand.size() - 1; i++) {
			if (maxCont < 4) {
				if (hand.get(i).get_num() - 1 == hand.get(i + 1).get_num()) {
					cont++;
				} else if (!gutShot && hand.get(i).get_num() - 2 == hand.get(i + 1).get_num()) {
					gutShot = true;
					cont++;
				} else if (gutShot && (hand.get(i).get_num() - 1 != hand.get(i + 1).get_num()
						&& hand.get(i).get_num() != hand.get(i + 1).get_num())) {
					gutShot = false;
					cont = 1;
				}
			}
			if (cont > maxCont)
				maxCont = cont;
		}

		if (maxCont == 3 && hand.get(hand.size() - 1).get_num() == 3 && hand.get(0).get_num() == 14) {
			ret = "Straight GutShot";
		} else if (maxCont == 3 && hand.get(hand.size() - 1).get_num() == 2 && hand.get(0).get_num() == 14) {
			if (gutShot)
				ret = "Straight GutShot";
			else
				ret = "Straight Open-ended";
		}

		if (maxCont == 4) {
			if (gutShot)
				ret = "Straight GutShot";
			else
				ret = "Straight Open-ended";
		}

		return ret;
	}

	public ArrayList<Card> checkPair(ArrayList<Card> hand) {
		ArrayList<Card> ret = new ArrayList<>();
		for (int i = 0; i < hand.size() - 1; i++) {
			if (hand.get(i).get_num() == hand.get(i + 1).get_num()) {
				ret.add(hand.get(i));
				ret.add(hand.get(i + 1));
				return ret;
			}
		}
		return ret;
	}

	public ArrayList<Card> checkTwoPair(ArrayList<Card> hand) {
		ArrayList<Card> ret = new ArrayList<>();
		for (int i = 0; i < hand.size() - 1; i++) {
			if (hand.get(i).get_num() == hand.get(i + 1).get_num()) {
				ret.add(hand.get(i));
				ret.add(hand.get(i + 1));
				if (ret.size() == 4)
					return ret;
			}
		}

		if (ret.size() != 4) {
			ret.clear();
		}
		return ret;
	}

	public ArrayList<Card> checkThreeOfaKind(ArrayList<Card> hand) {
		ArrayList<Card> ret = new ArrayList<>();
		int cont = 1;
		for (int i = 0; i < hand.size() - 1; i++) {
			if (cont == 3) {
				ret.add(hand.get(i));
				return ret;
			}

			if (hand.get(i).get_num() == hand.get(i + 1).get_num()) {
				ret.add(hand.get(i));
				cont++;
			} else {
				ret.clear();
				cont = 1;
			}
		}

		if (ret.size() != 0 && ret.get(0).get_num() == hand.get(hand.size() - 1).get_num()) {
			ret.add(hand.get(hand.size() - 1));
		}
			
		if (ret.size() != 3) {
			ret.clear();
		}
		return ret;
	}

	public ArrayList<Card> checkStraight(ArrayList<Card> hand) {
		ArrayList<Card> ret = new ArrayList<>();

		ret.add(hand.get(0));
		for (int i = 0; i < hand.size() - 1; ++i) {
			if (ret.size() == 5) {
				return ret;
			} else if (hand.get(i).get_num() - 1 != hand.get(i + 1).get_num()
					&& hand.get(i).get_num() != hand.get(i + 1).get_num()) {
				ret.clear();
				ret.add(hand.get(i + 1));
			} else if (hand.get(i).get_num() - 1 == hand.get(i + 1).get_num()) {
				ret.add(hand.get(i + 1));
			}
		}

		if (ret.size() == 4 && (hand.get(0).get_num() == 14 && ret.get(ret.size() - 1).get_num() == 2)) {
			ret.add(hand.get(0));
		} else if (ret.size() < 5)
			ret.clear();

		return ret;
	}

		public ArrayList<Card> checkFlush(ArrayList<Card> hand) {
			ArrayList<Card> ret = new ArrayList<>();
			ArrayList<Card> h = new ArrayList<>();
			
			for(Card c : hand)
				h.add(c);
		
			Hand.sortByColour(h);
			
			int counter = 1;
			for(int i = 0; i < h.size() - 1; i++) {
				
				if(h.get(i).getSuit().equals(h.get(i + 1).getSuit())) {
					ret.add(h.get(i));
					counter++;
				}
				else {
					ret.clear();
					counter = 1;
				}
				
				if(counter == 5) {
					ret.add(h.get(i + 1));
					return ret;
				}
			}
			
			if (counter == 4){
				ret.clear();
				ret.add(hand.get(counter));
				return ret;// flush draw
			}
			else if (counter < 4)
				ret.clear();
			
			return ret;
	}

	public ArrayList<Card> checkFull(ArrayList<Card> hand) {
		ArrayList<Card> ret = new ArrayList<>();
		int cont = 0;

		
		ret.addAll(checkThreeOfaKind(hand));
		
		if (ret.size() != 0) {
			
			ret.add(hand.get(0));
			int i = 1;
			
			while (i < hand.size() && cont != 1) {

				if ((ret.get(0).get_num() != hand.get(i).get_num()) && (ret.get(3).get_num() == hand.get(i).get_num())) {
					cont++;
					ret.add(hand.get(i));
				} else {
					ret.remove(ret.size() - 1);
					ret.add(hand.get(i));
				}
				++i;
			}
			
		}

		if (ret.size() != 5) {
			ret.clear();
		}

		return ret;
	}

	public ArrayList<Card> checkPoker(ArrayList<Card> hand) {
		ArrayList<Card> ret = new ArrayList<>();
		int cont = 1;
		for (int i = 0; i < hand.size() - 1; i++) {
			if (cont == 4) {
				ret.add(hand.get(i));
				return ret;
			}
			if (hand.get(i).get_num() == hand.get(i + 1).get_num()) {
				ret.add(hand.get(i));
				cont++;
			} else {
				ret.clear();
				cont = 1;
			}
		}
		ret.clear();
		return ret;
	}

	public ArrayList<Card> checkStraightFlush(ArrayList<Card> hand) {
		ArrayList<Card> ret = new ArrayList<>();

		ret.add(hand.get(0));
		for (int i = 0; i < hand.size() - 1; ++i) {
			if (hand.get(i).get_num() - 1 != hand.get(i + 1).get_num()
					|| !hand.get(i).get_suit().equals(hand.get(i + 1).get_suit())) {
				ret.clear();
			}
			ret.add(hand.get(i + 1));
		}

		if (ret.size() == 4 && ((hand.get(0).get_num() == 14 && ret.get(ret.size() - 1).get_num() == 2))) {
			int j = 0;

			while (j < hand.size() - 1) {
				if (hand.get(j).get_num() == 14 && hand.get(j).get_suit().equals(ret.get(0).get_suit())) {
					ret.add(hand.get(j));
					return ret;
				}
				j++;
			}

		} else if (ret.size() < 4)
			ret.clear();

		return ret;
	}

	public String checkHand(ArrayList<Card> hand) {

		ArrayList<Card> a = new ArrayList<>();
		String aux = "";

		String draws = "";
		String s = "Flush\n";
		
		boolean straight = false;
		
		a = checkStraightFlush(hand);
		if (!a.isEmpty()) {
			straight = true;
			aux = "Straight Flush of " + dictionary.get(a.get(0).get_suit()) + " (";
			for (Card c : a) {
				aux += c.get_id();
			}
			aux += ")";
		}

		a = checkPoker(hand);
		if (!a.isEmpty() && aux.equals("")) {
			aux = "Quads of " + a.get(0).toString() + (a.get(0).get_num() == 6 ? "es" : "s") + " (";
			for (Card c : a) {
				aux += c.get_id();
			}
			aux += ")";
		}

		a = checkFull(hand);
		if (!a.isEmpty() && aux.equals("")) {
			aux = "Full House of " + a.get(0).toString() + (a.get(0).get_num() == 6 ? "es" : "s") + " over " + a.get(a.size() - 1).toString() + (a.get(a.size() - 1).get_num() == 6 ? "es" : "s") + " (";
			for (Card c : a) {
				aux += c.get_id();
			}
			aux += ")";
		}

		a = checkFlush(hand);
		
		if(a.size() == 1) {
			draws += s;
			a.clear();
		}	
		
		if (!a.isEmpty() && aux.equals("")) {
			aux = "Flush of " + dictionary.get(hand.get(0).get_suit()) + " (";
			for (Card c : a) {
				aux += c.get_id();
			}
			aux += ")";
		}

		a = checkStraight(hand);
		if (!a.isEmpty() && aux.equals("")) {
			straight = true;
			aux = "Straight (";
			for (Card c : a) {
				aux += c.get_id();
			}
			aux += ")";
		}

		a = checkThreeOfaKind(hand);
		if (!a.isEmpty() && aux.equals("")) {
			aux = "Three of " + a.get(0).toString() + (a.get(0).get_num() == 6 ? "es" : "s") +" (";
			for (Card c : a) {
				aux += c.get_id();
			}
			aux += ")";
		}

		a = checkTwoPair(hand);
		if (!a.isEmpty() && aux.equals("")) {
			aux = "Two pair of " + a.get(0).toString() + (a.get(0).get_num() == 6 ? "es" : "s") + " and " + a.get(a.size() - 1).toString() + (a.get(a.size() - 1).get_num() == 6 ? "es" : "s") + " (";
			for (Card c : a) {
				aux += c.get_id();
			}
			aux += ")";
		}

		a = checkPair(hand);
		if (!a.isEmpty() && aux.equals("")) {
			aux = "Pair of " + a.get(0).toString() + (a.get(0).get_num() == 6 ? "es" : "s") + " (";
			for (Card c : a) {
				aux += c.get_id();
			}
			aux += ")";
		}

		if (aux.equals("")) {
				a.add(hand.get(0));

			aux = "High card (" + a.get(0).toString() + ")";
		}
		
		if (!straight && !checkDraws(hand).equals(""))
			aux += "\n  - Draw: " + checkDraws(hand);
		
		if(draws != "") {
			aux += "\n  - Draw: " + draws;
		}

		return aux;
	}
	
	public String checkHand(ArrayList<Card> hand, String player) {

		ArrayList<Card> a = new ArrayList<>();
		String aux = "";
		String s = "";

		a = checkStraightFlush(hand);
		if (!a.isEmpty()) {
			for (Card c : a) {
				aux += c.get_id();
			}
			
			aux += " (Straight Flush)";
		}

		a = checkPoker(hand);
		if (!a.isEmpty() && aux.equals("")) {
			for (Card c : a) {
				aux += c.get_id();
			}
			
			int cont = 0;
			while (cont != 7) {
				if (!hand.get(cont).get_value().equals(a.get(0).get_value())) {
					aux += hand.get(cont).get_id();
					cont = 6;
				}
				++cont;
			}
			
			aux += " (Quads)";
		}

		a = checkFull(hand);
		if (!a.isEmpty() && aux.equals("")) {
			for (Card c : a) {
				aux += c.get_id();
			}
			aux += 	" (Full House)";
		}

		a = checkFlush(hand);
		if (!a.isEmpty() && aux.equals("")) {
			for (Card c : a) {
				aux += c.get_id();
			}
			aux += " (Flush)";
		}

		a = checkStraight(hand);
		if (!a.isEmpty() && aux.equals("")) {
			for (Card c : a) {
				aux += c.get_id();
			}
			aux += " (Straight)";
		}

		a = checkThreeOfaKind(hand);
		if (!a.isEmpty() && aux.equals("")) {
			for (Card c : a) {
				aux += c.get_id();
			}
			
			int cont = 0;
			int cont2 = 0;
			while (cont != 7 && cont2 != 2) {
				if (!hand.get(cont).get_value().equals(a.get(0).get_value())) {
					aux += hand.get(cont).get_id();
					++cont2;
				}
				++cont;
			}
			
			aux += " (Three of " + a.get(0).toString() + (a.get(0).get_num() == 6 ? "es" : "s") + ")";
		}

		a = checkTwoPair(hand);
		if (!a.isEmpty() && aux.equals("")) {
			for (Card c : a) {
				aux += c.get_id();
			}
			
			int cont = 0;
			while (cont != 7) {
				if ((!hand.get(cont).get_value().equals(a.get(0).get_value())) && (!hand.get(cont).get_value().equals(a.get(2).get_value()))) {
					aux += hand.get(cont).get_id();
					cont = 6;
				}
				++cont;
			}
			
			aux += " (Two pair of " + a.get(0).toString() + (a.get(0).get_num() == 6 ? "es" : "s") + " and " + a.get(a.size() - 1).toString() + (a.get(a.size() - 1).get_num() == 6 ? "es" : "s") + ")";
		}

		a = checkPair(hand);
		if (!a.isEmpty() && aux.equals("")) {
			for (Card c : a) {
				aux += c.get_id();
			}
			
			int cont = 0;
			int cont2 = 0;
			while (cont != 7 && cont2 != 3) {
				if (!hand.get(cont).get_value().equals(a.get(0).get_value())) {
					aux += hand.get(cont).get_id();
					++cont2;
				}
				++cont;
			}
			
			aux += " (Pair of " + a.get(0).toString() +  (a.get(0).get_num() == 6 ? "es" : "s") + ")";
		}

		if (aux.equals("")) {
			if (hand.get(hand.size() - 1).get_num() == 1)
				a.add(hand.get(hand.size() - 1));
			else
				a.add(hand.get(0));
			
			int cont = 1;

			while (cont != 5) {
				aux += hand.get(cont).get_id();
				++cont;
			}
			
			aux += " (High card: " + a.get(0).toString() + ")";
		}

		return aux;
	}

}
