package sirttas.elementalcraft.pureore.injector;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import sirttas.elementalcraft.api.pureore.injector.AbstractPureOreRecipeInjector;
import sirttas.elementalcraft.tag.ECTags;

public class PureOreCookingRecipeInjector<T extends AbstractCookingRecipe> extends AbstractPureOreRecipeInjector<Container, T> {

	private final Factory<T> factory;

	public PureOreCookingRecipeInjector(RecipeType<T> recipeType, Factory<T> factory) {
		super(recipeType, false);
		this.factory = factory;
	}

	@Override
	public T build(T recipe, Ingredient ingredient) {
		return factory.create(buildRecipeId(recipe.getId()), recipe.getGroup(), ingredient, getRecipeOutput(recipe), recipe.getExperience(), recipe.getCookingTime());
	}

	public interface Factory<T extends AbstractCookingRecipe> {
		T create(ResourceLocation id, String group, Ingredient ingredient, ItemStack result, float experience, int cookTime);
	}

	@Override
	public boolean filter(T recipe, ItemStack stack) {
		return super.filter(recipe, stack) && (stack.is(ECTags.Items.PURE_SOURCE_ORES_ORES) || stack.is(ECTags.Items.PURE_ORES_SPECIFICS));
	}
}
