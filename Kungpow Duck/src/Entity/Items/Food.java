/*
 * Description: 		Base Class for all food. Contains an array that holds the ingredients
 * Date Created: 		6/7/2015
 * Date Last Modified: 	6/7/2015
 * Modification Notes:	
 */

package Entity.Items;

import Entity.Item;
import java.io.Serializable;

public class Food extends Item implements Serializable {
	
	private Item[] ingredients;
	private int ingredientCount;
	
	public Food() {
		type = Item.FOOD;
		canUse = false;
		canEat = true;
		canEquip = false;
		stackable = false;
		
		ingredientCount = 0;
		ingredients = new Item[3];
		setIcon("food.png");
	}
	
	public Item[] getIngredients() { return ingredients; }
	public void changeIngredients(Item[] ingredients) { this.ingredients = ingredients; }
	public void addIngredient(Item ingredient) {
		if(ingredientCount == 3){
			System.out.println("Please change the implementation of food to satisfy this requirement");
			System.out.println("Ingredient not added");
		}
		else{
			ingredients[ingredientCount] = ingredient;
			ingredientCount++;
		}
	}
}
