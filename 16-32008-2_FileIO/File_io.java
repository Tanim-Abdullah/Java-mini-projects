import java.io.*;
import java.lang.*;
import java.util.*;

// This is a project to see how java behaves regarding taking input output from and to a file.

public class File_io {
	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		InputStreamReader ISR = new InputStreamReader(System.in);
		BufferedReader buf = new BufferedReader(ISR);
		
		File f = new File("Input.txt"); // Storing inout to a file.
		f.createNewFile();
		FileWriter wr = new FileWriter(f); 
		
		char uc;
		String s = " ", t;
		System.out.println("Want to write ? (Y/N)"); // If the user wants to provide input here it will insert the input.
		uc = sc.next().charAt(0);
		
		while(uc == 'Y' || uc == 'y') // Checking if the user is done with inoput or wants to continue
		{
			t=buf.readLine();
			s=s+t+"\r"+"\n";
			System.out.println("Continue? (Y/N)");
			uc = sc.next().charAt(0);
		}
		wr.write(s);
		wr.flush();
		wr.close();
		
		FileReader r = new FileReader(f);
		buf = new BufferedReader (r);
		
		s= " ";
		int a=0, v=0, c=0, w=0, sen=0, l=0; 
		// This variables are for counting the content of input.
		// a= Alphabet, v=Vowels, c = Consonants, w=Words, sen= sentences, l= Lines.
		while((t=buf.readLine())!=null)
		{
			for(int i=0;i<t.length();i++)
			{
				char ch = t.charAt(i);
				if((ch>=65 && ch<=90) || (ch>=97 && ch<=122)) // checking if it is vowels by using ASCII value
				{
					a++;
				    if(ch=='A' || ch=='E' || ch=='I' || ch=='O' || ch=='U' || ch=='a' || ch=='e' || ch=='i' || ch=='o' || ch=='u') 
						v++;
					else c++; //othewise its a Constant
				}
				if(ch==' ') 
					w++;
				if(ch=='.')
					sen++;
			}
			l++;
			w++;
			s=s+t+'\n'+'\r';
		}
		r.close();
		
		File f2 = new File("Output.txt");//Output file data will be stored here
		f2.createNewFile();
		wr = new FileWriter(f2);
		s= "Number of alphabets: "+a+"\r"+"\n"+"Number of vowels: "+v+"\r"+"\n"+
		   "Number of consonants: "+c+"\r"+"\n"+"Number of words: "+w+"\r"+"\n"+
		   "Number of sentences: "+sen+"\r"+"\n"+"Number of lines: "+l;
		
		
		wr.write(s);
		wr.flush();
		wr.close();
		
		
	}
}