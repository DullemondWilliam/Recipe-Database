package java_sqlite_3005;

public class Recipe {

	private String name;
	private int    difficulty; 
	private int    size;
	private int    recipe_ID;
	


	public Recipe(int id, String n, int d, int s){
		name 		= n;
		difficulty	= d;
		size		= s;
		recipe_ID   = id;
	}
	
	public String getName() 	 	{return name;}
	public int    getDifficulty()	{return difficulty;}
	public int    getSize() 	 	{return size;}
	public int 	  getRecipe_ID()	{return recipe_ID;	}
		
	public String toString(){
		return name;
	}
}
