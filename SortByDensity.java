
public class SortByDensity implements Runnable{

	int left = 0, right = 0;
	Item [] items = null;
	
	public SortByDensity(Item [] items, int left, int right) {
		this.items = items;
		this.left = left;
		this.right = right;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		quicksort(items, left, right);
		
	}
	public int partition(Item [] items, int left, int right) {
		
		int i = left, j = right;
		double pivot = items[(left + right)/2].getRatio();
		
		Item temp;
		
		while(i <= j){
			while(items[i].getRatio() < pivot)
				i++;
			
			while(items[j].getRatio() > pivot)
				j--;

			if(i <= j){
				temp = items[j];
				items[j] = items[i];
				items[i] = temp;
				i++;
				j--;
			}
		}		
		return i;
	}
	
	public void quicksort(Item [] items, int left, int right){
		int index = partition(items, left, right);
		if(left < index - 1){
			Thread th = new Thread(new SortByDensity(items, left, index - 1));
			th.start();
			try {
				th.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(index < right){
			Thread th = new Thread(new SortByDensity(items, index, right));
			th.start();
			try {
				th.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
