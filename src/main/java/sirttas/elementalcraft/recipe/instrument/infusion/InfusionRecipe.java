package sirttas.elementalcraft.recipe.instrument.infusion;

import com.google.gson.JsonObject;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;
import sirttas.elementalcraft.api.ElementalCraftApi;
import sirttas.elementalcraft.api.element.ElementType;
import sirttas.elementalcraft.api.name.ECNames;
import sirttas.elementalcraft.block.instrument.infuser.IInfuser;
import sirttas.elementalcraft.recipe.RecipeHelper;
import sirttas.elementalcraft.recipe.instrument.AbstractInstrumentRecipe;

public class InfusionRecipe extends AbstractInstrumentRecipe<IInfuser> implements IInfusionRecipe {

	@ObjectHolder(ElementalCraftApi.MODID + ":" + NAME) public static final RecipeSerializer<InfusionRecipe> SERIALIZER = null;

	private final Ingredient input;
	private final ItemStack output;
	private final int elementAmount;

	public InfusionRecipe(ResourceLocation id, ElementType type, int elementAmount, ItemStack output, Ingredient input) {
		super(id, type);
		this.input = input;
		this.output = output;
		this.elementAmount = elementAmount;
	}

	@Override
	public int getElementAmount() {
		return elementAmount;
	}
	
	@Override
	public Ingredient getInput() {
		return input;
	}

	@Override
	public ItemStack getResultItem() {
		return output;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<InfusionRecipe> {

		@Override
		public InfusionRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			ElementType type = ElementType.byName(GsonHelper.getAsString(json, ECNames.ELEMENT_TYPE));
			int elementAmount = GsonHelper.getAsInt(json, ECNames.ELEMENT_AMOUNT);
			Ingredient input = RecipeHelper.deserializeIngredient(json, ECNames.INPUT);
			ItemStack output = RecipeHelper.readRecipeOutput(json, ECNames.OUTPUT);

			return new InfusionRecipe(recipeId, type, elementAmount, output, input);
		}

		@Override
		public InfusionRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			ElementType type = ElementType.byName(buffer.readUtf());
			int elementAmount = buffer.readInt();
			Ingredient input = Ingredient.fromNetwork(buffer);
			ItemStack output = buffer.readItem();

			return new InfusionRecipe(recipeId, type, elementAmount, output, input);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, InfusionRecipe recipe) {
			buffer.writeUtf(recipe.getElementType().getSerializedName());
			buffer.writeInt(recipe.getElementAmount());
			recipe.getInput().toNetwork(buffer);
			buffer.writeItem(recipe.getResultItem());
		}
	}
}
