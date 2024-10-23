package logic;

import java.util.*;

import objects.Card;


public class HandComparator implements Comparator<String>{

	    @Override
	    public int compare(String handInfo1, String handInfo2) {

	        String[] handRanking = {
	            "Straight Flush", "Quads", "Full House",
	            "Flush", "Straight", "Three", "Two", "Pair", "High"
	        };

	        String handType1 = getHandType(handInfo1);
	        String handType2 = getHandType(handInfo2);

	        int rank1 = Arrays.asList(handRanking).indexOf(handType1);
	        int rank2 = Arrays.asList(handRanking).indexOf(handType2);

	        
	        if (rank1 < rank2) {
	            return -1;
	        } else if (rank1 > rank2) {
	            return 1;
	        } else {
	        	int index1 = handInfo1.indexOf(' ');
	        	int index2 = handInfo2.indexOf(' ', index1 + 1);
	        	
	               	
	        	Card c1 = new Card(handInfo1.valueOf(handInfo1.charAt(index1 + 1)), handInfo1.valueOf(handInfo1.charAt(index1 + 2)), handInfo1.valueOf(handInfo1.charAt(index1 + 1)) + handInfo1.valueOf(handInfo1.charAt(index1 + 2)));  
	        	Card c2 = new Card(handInfo2.valueOf(handInfo2.charAt(index1 + 1)), handInfo2.valueOf(handInfo2.charAt(index1 + 2)), handInfo2.valueOf(handInfo2.charAt(index1 + 1)) + handInfo2.valueOf(handInfo2.charAt(index1 + 2)));  

	        	if(c1.get_num() < c2.get_num()) {
	        		return 1;
	        	}
	        	else if (c1.get_num() > c2.get_num()) {
	        		return -1;
	        	}
	        	else {//for full house if pair is the same
	        		Card c3 = new Card(handInfo1.valueOf(handInfo1.charAt(index1 + 7)), handInfo1.valueOf(handInfo1.charAt(index1 + 8)), handInfo1.valueOf(handInfo1.charAt(index1 + 7)) + handInfo1.valueOf(handInfo1.charAt(index1 + 8)));  
		        	Card c4 = new Card(handInfo2.valueOf(handInfo2.charAt(index1 + 7)), handInfo2.valueOf(handInfo2.charAt(index1 + 8)), handInfo2.valueOf(handInfo2.charAt(index1 + 7)) + handInfo2.valueOf(handInfo2.charAt(index1 + 8)));  
		        	
		        	if(c3.get_num() < c4.get_num()) {
		        		return 1;
		        	}
		        	else {
		        		return -1;
		        	}
	        		
	        	}
	        }
	    }

	    
	    private String getHandType(String handInfo) {
	        //"J#: Cartas (Mano)"
	        int startIndex = handInfo.indexOf('(') + 1;
	        int endIndex = handInfo.indexOf(' ', startIndex + 1);
	        
	        if (endIndex != -1) {
	        	String aux = handInfo.substring(startIndex, endIndex).trim();
		        if (!aux.equals("Three") && !aux.equals("Two") && !aux.equals("Pair") && !aux.equals("High")) endIndex = handInfo.indexOf(')') - 1;
	        }
	        else endIndex = handInfo.indexOf(')');
	        
	        if (startIndex > 0 && endIndex > startIndex) {
	            return handInfo.substring(startIndex, endIndex).trim();
	        }
	        return "";
	    }

}
