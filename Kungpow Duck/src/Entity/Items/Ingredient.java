/*
 * Description: 		Base Class for all ingredients. Holds an array that shows what recipes the ingredient can be used in.
 * Date Created: 		6/7/2015
 * Date Last Modified: 	6/7/2015
 * Modification Notes:	
 */

package Entity.Items;

import Entity.Item;
import java.io.Serializable;

public class Ingredient extends Item implements Serializable {
	private Item[] recipes;
	private int recipeCount;
	
	public Ingredient() {
		type = Item.INGREDIENT;
		canUse = false;
		canEat = false; // This can be changed within the individual item, if the ingredient can be eaten
		canEquip = false;
		stackable = true; // This can be changed within the individual item, as necessary.
	
		recipeCount = 0;
		recipes = new Item[5];
		
		setIcon("ingredient.png");
	}
	
	public Item[] getRecipes() { return recipes; }
	public void addRecipe(Item recipe) {
		if(recipeCount == 5){
			System.out.println("Recipe not added");
			System.out.println("Please change the implementation of Ingredient");
		}
		else{
			recipes[recipeCount] = recipe;
			recipeCount++;
		}
	}
	
}
