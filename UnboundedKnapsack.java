import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class UnboundedKnapsack {

	public static void main(String [] args) {
		
		long startTime = System.currentTimeMillis();

		if(args.length != 1) {
			System.out.println("Usage: <input>");
			return;
			}
		
		String input = args[0];
		
		Item [] items = null;
		int itemsNumber = 0;
		int knapsackCapacity = 0;
		
		try {
			FileReader fr = new FileReader(input);
			BufferedReader br = new BufferedReader(fr);
			
			String firstLine;
			String str = "";
			
			firstLine = br.readLine();
			
			StringTokenizer st1 = new StringTokenizer(firstLine, " ");
			
			itemsNumber = Integer.valueOf(st1.nextToken());
			knapsackCapacity = Integer.valueOf(st1.nextToken());
			
			items = new Item[itemsNumber];
			
			int counter = 0;
			while((str = br.readLine()) != null) {
				StringTokenizer st2 = new StringTokenizer(str, " ");
				items[counter] = new Item(Integer.valueOf(st2.nextToken()), Integer.valueOf(st2.nextToken()));
				counter++;
			}
			
			br.close();
			
		} catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch(IOException io) {
			io.printStackTrace();
		}

		ArrayList<Item> al = new ArrayList<Item>(Arrays.asList(items));
		
		Map<Integer, Boolean> ht = new HashMap<Integer, Boolean>();
		
		for(int i = 0; i < al.size(); i++)
			for(int j = i + 1; j < al.size(); j++) {
				if(al.get(i).getWeight() <= al.get(j).getWeight() 
						&& al.get(i).getValue() >= al.get(j).getValue())
					ht.put(j, true);
			}
		int n = 0;
		for(int i: ht.keySet()) {
			al.remove(i - n);
			n++;
		}
		
		Item [] itemsAfter = new Item[al.size()];
		itemsAfter = al.toArray(itemsAfter);
		
		SortByDensity sbd = new SortByDensity(itemsAfter, 0, itemsAfter.length - 1);		
		Thread thread = new Thread(sbd);
		
		thread.start();
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		for(Item item: itemsAfter)
			System.out.println("Value: " + item.getValue() + ", Weight: " + item.getWeight() + 
					", Ratio: " + item.getRatio());		
		*/
		Map<Integer, Integer> finalMap = new HashMap<Integer, Integer>();
		
		double weight = 0;
		int counter = itemsAfter.length - 1;
		
		int finalValue = 0;
		while(weight < knapsackCapacity) {
			
			while(weight + itemsAfter[counter].getWeight() > knapsackCapacity)
				counter--;
			
			weight += itemsAfter[counter].getWeight();
			finalValue += itemsAfter[counter].getValue();
			
			if(finalMap.get(itemsAfter[counter].getWeight()) == null)
				finalMap.put(itemsAfter[counter].getWeight(), 1);
			else {
				int temp = finalMap.get(itemsAfter[counter].getWeight());
				finalMap.put(itemsAfter[counter].getWeight(), temp + 1);
			}
		}				
		//for(int i: finalMap.keySet())
			//System.out.println("Item weight: " + i + ", quantity: " + finalMap.get(i));		
		
		System.out.println(finalValue);
		
		long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println("The time spent is: "  + elapsedTime);
	}
}
