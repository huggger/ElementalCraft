package sirttas.elementalcraft.pureore.injector;

import net.minecraft.world.item.crafting.Ingredient;
import sirttas.elementalcraft.api.pureore.injector.AbstractPureOreRecipeInjector;
import sirttas.elementalcraft.block.instrument.io.mill.grindstone.AirMillGrindstoneBlockEntity;
import sirttas.elementalcraft.container.ContainerBlockEntityWrapper;
import sirttas.elementalcraft.recipe.ECRecipeTypes;
import sirttas.elementalcraft.recipe.instrument.io.grinding.AirMillGrindingRecipe;
import sirttas.elementalcraft.recipe.instrument.io.grinding.IGrindingRecipe;

public class PureOreGrindingRecipeInjector extends AbstractPureOreRecipeInjector<ContainerBlockEntityWrapper<AirMillGrindstoneBlockEntity>, IGrindingRecipe> {

    protected PureOreGrindingRecipeInjector() {
        super(ECRecipeTypes.AIR_MILL_GRINDING.get(), true);
    }

    @Override
    public IGrindingRecipe build(IGrindingRecipe recipe, Ingredient ingredient) {
        return new AirMillGrindingRecipe(buildRecipeId(recipe.getId()), ingredient, getRecipeOutput(recipe), recipe.getElementAmount(), recipe instanceof AirMillGrindingRecipe airMillGrindingRecipe ? airMillGrindingRecipe.luckRation() : 0);
    }
}
