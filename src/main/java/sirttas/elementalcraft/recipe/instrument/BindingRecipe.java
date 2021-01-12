package sirttas.elementalcraft.recipe.instrument;

import java.util.List;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;
import sirttas.elementalcraft.ElementalCraft;
import sirttas.elementalcraft.api.element.ElementType;
import sirttas.elementalcraft.api.name.ECNames;
import sirttas.elementalcraft.block.instrument.binder.TileBinder;
import sirttas.elementalcraft.config.ECConfig;
import sirttas.elementalcraft.recipe.RecipeHelper;

public class BindingRecipe extends AbstractInstrumentRecipe<TileBinder> {

	public static final String NAME = "binding";
	public static final IRecipeType<BindingRecipe> TYPE = Registry.register(Registry.RECIPE_TYPE, ElementalCraft.createRL(NAME), new IRecipeType<BindingRecipe>() {
		@Override
		public String toString() {
			return NAME;
		}
	});
	@ObjectHolder(ElementalCraft.MODID + ":" + NAME) public static IRecipeSerializer<BindingRecipe> SERIALIZER;

	private NonNullList<Ingredient> ingredients;
	private ItemStack output;
	private int elementAmount;

	public BindingRecipe(ResourceLocation id, ElementType type, int elementAmount, ItemStack output, List<Ingredient> ingredients) {
		super(id, type);
		this.ingredients = NonNullList.from(Ingredient.EMPTY, ingredients.stream().toArray(s -> new Ingredient[s]));
		this.output = output;
		this.elementAmount = elementAmount;
	}

	@Override
	public int getElementAmount() {
		return elementAmount;
	}

	@Override
	public boolean matches(TileBinder inv) {
		if (inv.getTankElementType() != getElementType() || inv.getItemCount() != ingredients.size()) {
			return false;
		}
		return Boolean.TRUE.equals(ECConfig.COMMON.binderRecipeMatchOrder.get()) ? matchesOrdered(inv) : RecipeHelper.matchesUnordered(inv.getInventory(), ingredients);
	}

	private boolean matchesOrdered(TileBinder inv) {
		for (int i = 0; i < inv.getItemCount(); i++) {
			if (!ingredients.get(i).test(inv.getInventory().getStackInSlot(i))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return ingredients;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return output;
	}

	@Override
	public IRecipeType<?> getType() {
		return TYPE;
	}

	@Override
	public void process(TileBinder instrument) {
		instrument.clear();
		super.process(instrument);
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<BindingRecipe> {
		final IRecipeFactory factory;

		public Serializer(IRecipeFactory factory) {
			this.factory = factory;
		}

		@Override
		public BindingRecipe read(ResourceLocation recipeId, JsonObject json) {
			ElementType type = ElementType.byName(JSONUtils.getString(json, ECNames.ELEMENT_TYPE));
			int elementAmount = JSONUtils.getInt(json, ECNames.ELEMENT_AMOUNT);
			NonNullList<Ingredient> ingredients = RecipeHelper.readIngredients(JSONUtils.getJsonArray(json, ECNames.INGREDIENTS));
			ItemStack output = RecipeHelper.readRecipeOutput(JSONUtils.getString(json, ECNames.OUTPUT));

			return this.factory.create(recipeId, type, elementAmount, output, ingredients);
		}

		@Override
		public BindingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
			ElementType type = ElementType.byName(buffer.readString(32767));
			int elementAmount = buffer.readInt();
			ItemStack output = buffer.readItemStack();
			int i = buffer.readVarInt();
			NonNullList<Ingredient> ingredients = NonNullList.withSize(i, Ingredient.EMPTY);

			for (int j = 0; j < ingredients.size(); ++j) {
				ingredients.set(j, Ingredient.read(buffer));
			}

			return this.factory.create(recipeId, type, elementAmount, output, ingredients);
		}

		@Override
		public void write(PacketBuffer buffer, BindingRecipe recipe) {
			buffer.writeString(recipe.getElementType().getString());
			buffer.writeInt(recipe.getElementAmount());
			buffer.writeItemStack(recipe.getRecipeOutput());
			buffer.writeVarInt(recipe.getIngredients().size());

			for (Ingredient ingredient : recipe.getIngredients()) {
				ingredient.write(buffer);
			}
		}

		public interface IRecipeFactory {
			BindingRecipe create(ResourceLocation id, ElementType type, int elementAmount, ItemStack output, List<Ingredient> ingredients);
		}
	}
}