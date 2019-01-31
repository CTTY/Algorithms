import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class Framework
{
	int n; // number of applicants (employers)

	int APrefs[][]; // preference list of applicants (n*n)
	int EPrefs[][]; // preference list of employers (n*n)

	ArrayList<MatchedPair> MatchedPairsList; // your output should fill this arraylist which is empty at start

	public class MatchedPair
	{
		int appl; // applicant's number
		int empl; // employer's number

		public MatchedPair(int Appl,int Empl)
		{
			appl=Appl;
			empl=Empl;
		}

		public MatchedPair()
		{
		}
	}

	// reading the input
	void input(String input_name)
	{
		File file = new File(input_name);
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(file));

			String text = reader.readLine();

			String [] parts = text.split(" ");
			n=Integer.parseInt(parts[0]);

			APrefs=new int[n][n];
			EPrefs=new int[n][n];

			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				String [] aList=text.split(" ");
				for (int j=0;j<n;j++)
				{
					APrefs[i][j]=Integer.parseInt(aList[j]);
				}
			}

			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				String [] eList=text.split(" ");
				for(int j=0;j<n;j++)
				{
					EPrefs[i][j]=Integer.parseInt(eList[j]);
				}
			}

			reader.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// writing the output
	void output(String output_name)
	{
		try
		{
			PrintWriter writer = new PrintWriter(output_name, "UTF-8");

			for(int i=0;i<MatchedPairsList.size();i++)
			{
				writer.println(MatchedPairsList.get(i).empl+" "+MatchedPairsList.get(i).appl);
			}

			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public Framework(String []Args)
	{
		input(Args[0]);

		MatchedPairsList=new ArrayList<MatchedPair>(); // you should put the final stable matching in this array list

		/* NOTE
		 * if you want to declare that man x and woman y will get matched in the matching, you can
		 * write a code similar to what follows:
		 * MatchedPair pair=new MatchedPair(x,y);
		 * MatchedPairsList.add(pair);
		*/

		//YOUR CODE STARTS HERE
		  //APrefs,  EPrefs    Pair(A,E)
		/** Initialize a LinkedList to store free employers  n*O(1)*/
		List<Integer> freeE = new LinkedList<>();
		for(int i=0;i<n;i++) {
			freeE.add(i);
		}
		
		/**Next: The next applicant that a emplyer will propose*/
		int[] Next = new int[n];
		Arrays.fill(Next, 0);
		
		/**Current: Current employer choosen by an applicant*/
		int[] Current = new int[n];
		Arrays.fill(Current, -1);
		
		/**Ranking: rank of employer in a sorted order  O(n^2)*/
		int[][] Ranking = new int[n][n];
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				Ranking[i][j] = indexOf(Apref[i],j);
			}
		}
		
		while(!freeE.isEmpty()) {
			
			for(int i=0;i<freeE.size();i++) {
				int curE=freeE.get(i);
				int curA=Eprefs[curE][Next[i]];
				if(Current[curA]==-1) {      //If the applicant is not paired
					Current[curA]=curE;     
					MatchedPair pair = new MatchedPair(curA,curE);
					MatchedPairsList.add(pair);
					freeE.remove(curE);
				}else {							//applicant is paired
					if(Ranking[curA][curE]>Ranking[curA][Current[curA]]) {  //if current emplyer is better
						MatchedPair temp = new MatchedPair(curA,Current[curA]);
						MatchedPairsList.remove(temp);     //remove old pair
						freeE.add(Current[curA]);			//readd replced employer to freeE
						temp = new MatchedPair(curA,curE);
						MatchedPairsList.add(temp);
						freeE.remove(curE);
					}
				}
				Next[i]++;
			}
		}
		
		
		//YOUR CODE ENDS HERE

		output(Args[1]);
	}
	
	private int indexOf(int[] nums, int target) {
		for(int i=0;i<nums.length;i++) {
			if(nums[i]==target) return i;
		}
		return -1;
	}

	public static void main(String [] Args) // Strings in Args are the name of the input file followed by the name of the output file
	{
		new Framework(Args);
	}
}
