package datastructure.kmp;

public class Test {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        
		String src="abcaabdaacadb";
		String sub="ababcbce";
		          //-101
		int[] next = KMP.getNext(sub);
		for(int i=0;i<next.length;i++)
		{
			System.out.print(next[i]+" ");
		}
		System.out.println("\n------------\n");
		System.out.println(KMP.kmp(src, sub));
		
	}

}
