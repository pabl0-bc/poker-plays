
package sportium;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//import logic.HandComparator;

import controller.Controller;
import logic.HandComparator;

public class Main {

	private static Controller ctrl;

	public static void main(String[] args) throws Exception {
		if (args.length != 3) {
			System.out.println("Argumentos incorrectos");
			return;
		}

		try {
			int numApartado = Integer.parseInt(args[0]);
			FileReader archivoReader = new FileReader(args[1]);
			BufferedReader reader = new BufferedReader(archivoReader);
			FileWriter archivoWriter = new FileWriter(args[2]);
//			BufferedWriter writer = new BufferedWriter(archivoWriter);
			ctrl = new Controller(archivoWriter);

			switch (numApartado) {
			case 1: {
				String hand = reader.readLine();
				String[] cards = new String[hand.length() / 2];
				ArrayList<String> input = new ArrayList<>();
				input.add(hand);

				if (hand.length() % 2 != 0) {
					System.out.println("Error al leer mano");
				} else {
					int j = 0;
					for (int i = 0; i < hand.length(); i += 2) {
						String parDeCaracteres = hand.substring(i, i + 2);
						cards[j] = parDeCaracteres;
						j++;
					}
				}
				reader.close();
				ctrl.buildHand(cards);
				ctrl.checkHand(input);
			}
				break;
			case 2: {
				String hand = reader.readLine();
				ArrayList<String> input = new ArrayList<>();
				input.add(hand);

				while (hand != null) {
					String[] cards = new String[(hand.length() / 2) - 1];
					String[] aux = new String[(hand.length() / 2) - 1];
					aux = hand.split(";");
					hand = aux[0] + aux[2];

					if (hand.length() % 2 != 0) {
						System.out.println("Error al leer mano");
					} else {
						int j = 0;
						for (int i = 0; i < hand.length(); i += 2) {
							String parDeCaracteres = hand.substring(i, i + 2);
							cards[j] = parDeCaracteres;
							j++;
						}

						ctrl.buildHand(cards);
					}
					hand = reader.readLine();
					input.add(hand);
				}
				ctrl.checkHand(input);
				reader.close();
			}
				break;
			case 3: {

				String hands = reader.readLine();
				archivoWriter.write(hands);

				while (hands != null) {
					String[] parts = hands.split(";");

					int numJ = Integer.parseInt(parts[0]);

					// Comprueba los argumentos con el numero de jugadores
					if (parts.length != (numJ + 2)) {
						System.out.println("Argumentos incorrectos");
						hands = reader.readLine();
						continue;
					}

					String[] jHands = new String[numJ];
					String commonCards = parts[numJ + 1];

					for (int i = 0; i < numJ; i++) {
						jHands[i] = parts[i + 1];
					}

					List<String> playerHandsInfo = new ArrayList<>();

					for (int i = 0; i < numJ; i++) {

						String allCards = jHands[i] + commonCards;
						String[] cardsJ = new String[7];

						int j = 0;
						for (int y = 2; y < allCards.length(); y += 2) {
							String parDeCaracteres = allCards.substring(y, y + 2);
							cardsJ[j] = parDeCaracteres;
							j++;
						}

						ctrl.buildHand(cardsJ);
						String bestHandInfo = ctrl.checkMultipleHands("J" + i);

						playerHandsInfo.add("J" + (i + 1) + ": " + bestHandInfo);
					}

					Collections.sort(playerHandsInfo, new HandComparator());

					ctrl.writeHands(playerHandsInfo);

					hands = reader.readLine();
				}

				reader.close();

			}
				break;
			}
		} catch (IOException e) {
			System.err.println("Error al leer el archivo: " + e.getMessage());
		}

	}

}
