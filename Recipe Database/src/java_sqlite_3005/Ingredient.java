package java_sqlite_3005;

public class Ingredient {

	private String name;
	private String foodGroup; 
	private int    shelfLife;
	private int    calories;
	private int    ingID;
	public Ingredient(int id,String n,String fg,int sl, int c){
		ingID		= id;
		name 		= n;
		foodGroup	= fg;
		shelfLife	= sl;
		calories	= c;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setFoodGroup(String foodGroup) {
		this.foodGroup = foodGroup;
	}

	public void setShelfLife(int shelfLife) {
		this.shelfLife = shelfLife;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public void setIngID(int ingID) {
		this.ingID = ingID;
	}

	public String getName()		{return name;}
	public String getFoodGroup(){return foodGroup;}
	public int    getShelfLife(){return shelfLife;}
	public int    getCalories() {return calories;}
	public int    getIngID()	{return ingID;}
	
	public String toString(){
		return "-"+name;
	}
}
