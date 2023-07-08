package csen1002.main.task7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;


/**
 * Write your info here
 * 
 * @name Omar Amin
 * @id 46-1014
 * @labNumber 23
 */

public class CfgLl1Parser {
	Stack<String> stack = new Stack<String>();
	String output;
	String input;
	String final1 ;
	  ArrayList<ContextFree> cf=new ArrayList<ContextFree>();
	   List<String> symbols = new ArrayList<String>();
	   List<String> states = new ArrayList<String>();
	   String[][] table;
	   List<String> symbols1 = new ArrayList<String>();
	public CfgLl1Parser(String input) {
		this.input=input;
	       List<String> list = new ArrayList<String>(Arrays.asList(this.input.split("#")));
			states = Arrays.asList(list.get(0).split(";"));
			symbols =Arrays.asList(list.get(1).split(";"));
			
			for(int i=0;i<symbols.size();i++) {
				symbols1.add(symbols.get(i));
			}
			symbols1.add("$");
			 table=new String[states.size()][symbols1.size()];

		  	List<String> transitions = new ArrayList<String>(Arrays.asList(list.get(2).split(";")));

			for(int i =0;i<states.size();i++) {
				ArrayList<String> c =new ArrayList<String>(Arrays.asList(transitions.get(i).split("/")));
				ArrayList<String> c1 =new ArrayList<String>(Arrays.asList(c.get(1).split(",")));
				 ArrayList<String> n=new ArrayList<String>();
				 ArrayList<Character> n1=new ArrayList<Character>();
				 
				 String[] n3= new String[10];
			
				this.cf.add(new ContextFree(c1,states.get(i),n,n1,n3));
	}
			List<String> first = new ArrayList<String>(Arrays.asList(list.get(3).split(";")));
		
			for(int i =0;i<cf.size();i++) {
				ArrayList<String> c =new ArrayList<String>(Arrays.asList(first.get(i).split("/")));
				ArrayList<String> c1 =new ArrayList<String>(Arrays.asList(c.get(1).split(",")));
				for(int j=0;j<c1.size();j++) {
					cf.get(i).first.add(c1.get(j));
				}
			}
			List<String> follow = new ArrayList<String>(Arrays.asList(list.get(4).split(";")));
			
			for(int i =0;i<cf.size();i++) {
				ArrayList<String> c =new ArrayList<String>(Arrays.asList(follow.get(i).split("/")));
				ArrayList<String> c1 =new ArrayList<String>(Arrays.asList(c.get(1).split(",")));
			
				for(int j=0;j<c1.size();j++) {
					for(int k=0;k<c1.get(j).length();k++) {
					cf.get(i).follow.add(c1.get(j).charAt(k));
					}
				}
			}
			
			
		
			
	}
	/**
	 * @param input The string to be parsed by the LL(1) CFG.
	 * 
	 * @return A string encoding a left-most derivation.
	 */
	public String parse(String input) {
		// TODO Auto-generated method stub
		for(int i=0;i<symbols1.size();i++) {
			for(int j=0;j<cf.size();j++) {
				
				for(int o=0;o<cf.get(j).first.size();o++) {
					if(cf.get(j).first.get(o).equals(symbols1.get(i))&& !symbols1.get(i).equals("e") && cf.get(j).first.get(o).length()==1 ) {
						String s=symbols1.get(i);
						for(int k=0;k<cf.get(j).states.size();k++) {
							if((cf.get(j).states.get(k).charAt(0)+"").equals(s)) {
//								
								table[j][i]=cf.get(j).states.get(k);
//								
							}
							
						}
					}
					else if(cf.get(j).first.get(o).length()>1) {
						String s= cf.get(j).states.get(o);
						
							for(int g=0;g<cf.get(j).first.get(o).length();g++) {
								String charac= cf.get(j).first.get(o).charAt(g)+"";
								int index = symbols1.indexOf(charac);
								table[j][index]=s;
							}
						}
						
						
						
					
					else {

						if(cf.get(j).states.contains("e") && cf.get(j).follow.contains(symbols1.get(i).charAt(0))) {
						table[j][i]="e";
						}
						if(cf.get(j).states.contains("e") && symbols1.get(i).equals("$") && cf.get(j).follow.contains('$')  ) {
							table[j][i]="e";
						}
					}
					
					
				}
				}
			}
		stack.push("$");
		input =input+"$";
		stack.push(states.get(0));
		ArrayList<String> xx= new ArrayList<String>();
		xx.add(states.get(0));
		boolean x=true;
		while(!input.isEmpty() && x) {

			if(input.charAt(0)=='$' && Character.isUpperCase(stack.peek().charAt(0)) ) {
				xx.add("ERROR");
				x=false;
				break;
			}

			
			if(input.length()>1 && stack.peek().equals("$") ) {
				xx.add("ERROR");
				x=false;
				break;
			}

			if(input.charAt(0)=='$' && !stack.peek().equals("$") ) {
				xx.add("ERROR");
				x=false;
				break;
			}
			
			//accept
			if(input.charAt(0)=='$' && stack.peek().equals("$") ) {
				x=false;
				break;
			}
			
			if( Character.isUpperCase(stack.peek().charAt(0))) {
				
			
				
				if(Character.isLowerCase(input.charAt(0))){
					int id=symbols1.indexOf(input.charAt(0)+"");
					int id2=states.indexOf(stack.peek().charAt(0)+"");
					
					System.out.println(id);
					System.out.println(id2);
					
					String newAdd=table[id2][id];
					System.out.println(newAdd+"new");
					
					if(newAdd == null ) {
						xx.add("ERROR");
						x=false;
						
					}
				
					else if(newAdd != null && !newAdd.equals("e")) {
						
						String s= xx.get(xx.size()-1);
						s= s.replaceFirst(stack.peek(), newAdd);
						xx.add(s);
						
						
						stack.pop();
						  for (int i = newAdd.length() - 1; i >= 0; i--) {
					            stack.push(newAdd.charAt(i)+"");
					        }
						  
						  
					}
					else if(newAdd.equals("e")) {
						
						String s= xx.get(xx.size()-1);
					s= s.replaceFirst(stack.peek(), "");
					xx.add(s);
						
						stack.pop();
					}
					
				}
			}
			else if(Character.isLowerCase(stack.peek().charAt(0))) {
				if(Character.isLowerCase(input.charAt(0))){
					if((stack.peek().charAt(0)+"").equals(input.charAt(0)+"") ) {
						input=input.substring(1);
						stack.pop();
					}
					else {
						xx.add("ERROR");
						x=false;
					
					}
				}
			}
			else {
				
			}
			System.out.println(stack);
			System.out.println(input);
			System.out.print(xx);
		}
		System.out.print(xx);
		String final2="";
		for(int i=0;i<xx.size();i++) {
			final2= final2 + xx.get(i)+";";
		}
		final2 = final2.substring(0, final2.length() - 1);

		return final2;
	}

	public static void main(String[]args) {
		CfgLl1Parser c= new CfgLl1Parser("S;F;G;T;X#g;l;o;p;u;y;z#S/FFTz,yS;F/lG,gF;G/yX,uS,e;T/oXTz,g,pSpG,e;X/pSlS,zS#S/gl,y;F/l,g;G/y,u,e;T/o,g,p,e;X/p,z#S/$glopz;F/gl;G/glz;T/z;X/glopz");
		c.parse("yyllzozl");
	}

}

class ContextFree {
	ArrayList<String> states;
	ArrayList<String> first;
	ArrayList<Character> follow;
	String[] table;
	String id;
	
	
	
  public ContextFree(ArrayList<String> states,String id ,	ArrayList<String> first , ArrayList<Character> follow, String [] table) {
	   this.states=states;   
	   this.id=id;
	   this.first=first;
	   this.follow=follow;
	   this.table=table;

   }

public ArrayList<String> getStates() {
	return states;
}


public void setStates(ArrayList<String> states) {
	this.states = states;
}



public void addState(String e) {
	if(!(this.states.contains(e))) {
		this.states.add(e);
	}
}
	
public void addFollow(Character e) {
	this.follow.add(e);
}

public void addTable(String e,int i) {
	System.out.print(e);
	this.table[i]=e;
}

public void addFirst(String e) {
	this.first.add(e);
}

public String getId() {
	return id;
}


public void setId(String id) {
	this.id = id;
}

}
