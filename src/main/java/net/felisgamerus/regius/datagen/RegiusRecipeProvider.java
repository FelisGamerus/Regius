package net.felisgamerus.regius.datagen;

import net.felisgamerus.regius.block.RegiusBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class RegiusRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public RegiusRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.SPHAGNUM_MOSS_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', RegiusBlocks.SPHAGNUM_MOSS.get())
                .unlockedBy("has_sphagnum_moss", has(RegiusBlocks.SPHAGNUM_MOSS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.DRIED_SPHAGNUM_MOSS_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', RegiusBlocks.DRIED_SPHAGNUM_MOSS.get())
                .unlockedBy("has_dried_sphagnum_moss", has(RegiusBlocks.DRIED_SPHAGNUM_MOSS)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.HOLLOW_OAK_LOG.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.OAK_LOG)
                .unlockedBy("has_oak_log", has(Blocks.OAK_LOG)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.HOLLOW_STRIPPED_OAK_LOG.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.STRIPPED_OAK_LOG)
                .unlockedBy("has_stripped_oak_log", has(Blocks.STRIPPED_OAK_LOG)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.HOLLOW_SPRUCE_LOG.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.SPRUCE_LOG)
                .unlockedBy("has_oak_log", has(Blocks.SPRUCE_LOG)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.HOLLOW_STRIPPED_SPRUCE_LOG.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.STRIPPED_SPRUCE_LOG)
                .unlockedBy("has_stripped_oak_log", has(Blocks.STRIPPED_SPRUCE_LOG)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.HOLLOW_BIRCH_LOG.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.BIRCH_LOG)
                .unlockedBy("has_oak_log", has(Blocks.BIRCH_LOG)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.HOLLOW_STRIPPED_BIRCH_LOG.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.STRIPPED_BIRCH_LOG)
                .unlockedBy("has_stripped_oak_log", has(Blocks.STRIPPED_BIRCH_LOG)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.HOLLOW_JUNGLE_LOG.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.JUNGLE_LOG)
                .unlockedBy("has_oak_log", has(Blocks.JUNGLE_LOG)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.HOLLOW_STRIPPED_JUNGLE_LOG.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.STRIPPED_JUNGLE_LOG)
                .unlockedBy("has_stripped_oak_log", has(Blocks.STRIPPED_JUNGLE_LOG)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.HOLLOW_ACACIA_LOG.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.ACACIA_LOG)
                .unlockedBy("has_oak_log", has(Blocks.ACACIA_LOG)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.HOLLOW_STRIPPED_ACACIA_LOG.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.STRIPPED_ACACIA_LOG)
                .unlockedBy("has_stripped_oak_log", has(Blocks.STRIPPED_ACACIA_LOG)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.HOLLOW_DARK_OAK_LOG.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.DARK_OAK_LOG)
                .unlockedBy("has_oak_log", has(Blocks.DARK_OAK_LOG)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.HOLLOW_STRIPPED_DARK_OAK_LOG.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.STRIPPED_DARK_OAK_LOG)
                .unlockedBy("has_stripped_oak_log", has(Blocks.STRIPPED_DARK_OAK_LOG)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.HOLLOW_MANGROVE_LOG.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.MANGROVE_LOG)
                .unlockedBy("has_oak_log", has(Blocks.MANGROVE_LOG)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.HOLLOW_STRIPPED_MANGROVE_LOG.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.STRIPPED_MANGROVE_LOG)
                .unlockedBy("has_stripped_oak_log", has(Blocks.STRIPPED_MANGROVE_LOG)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.HOLLOW_CHERRY_LOG.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.CHERRY_LOG)
                .unlockedBy("has_oak_log", has(Blocks.CHERRY_LOG)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.HOLLOW_STRIPPED_CHERRY_LOG.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.STRIPPED_CHERRY_LOG)
                .unlockedBy("has_stripped_oak_log", has(Blocks.STRIPPED_CHERRY_LOG)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.HOLLOW_CRIMSON_STEM.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.CRIMSON_STEM)
                .unlockedBy("has_oak_log", has(Blocks.CRIMSON_STEM)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.HOLLOW_STRIPPED_CRIMSON_STEM.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.STRIPPED_CRIMSON_STEM)
                .unlockedBy("has_stripped_oak_log", has(Blocks.STRIPPED_CRIMSON_STEM)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.HOLLOW_WARPED_STEM.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.WARPED_STEM)
                .unlockedBy("has_oak_log", has(Blocks.WARPED_STEM)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RegiusBlocks.HOLLOW_STRIPPED_WARPED_STEM.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Blocks.STRIPPED_WARPED_STEM)
                .unlockedBy("has_stripped_oak_log", has(Blocks.STRIPPED_WARPED_STEM)).save(recipeOutput);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RegiusBlocks.SPHAGNUM_MOSS.get(), 9)
                .requires(RegiusBlocks.SPHAGNUM_MOSS_BLOCK)
                .unlockedBy("has_sphagnum_moss_block", has(RegiusBlocks.SPHAGNUM_MOSS_BLOCK)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RegiusBlocks.DRIED_SPHAGNUM_MOSS.get(), 9)
                .requires(RegiusBlocks.DRIED_SPHAGNUM_MOSS_BLOCK)
                .unlockedBy("has_dried_sphagnum_moss_block", has(RegiusBlocks.DRIED_SPHAGNUM_MOSS_BLOCK)).save(recipeOutput);


        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RegiusBlocks.SPHAGNUM_MOSS.get()), RecipeCategory.MISC, RegiusBlocks.DRIED_SPHAGNUM_MOSS.get(), 0.15F, 200)
                .unlockedBy("has_sphagnum_moss", has(RegiusBlocks.SPHAGNUM_MOSS.get()))
                .save(recipeOutput, "regius:dried_sphagnum_moss_from_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RegiusBlocks.SPHAGNUM_MOSS_BLOCK.get()), RecipeCategory.MISC, RegiusBlocks.DRIED_SPHAGNUM_MOSS_BLOCK.get(), 0.15F, 200)
                .unlockedBy("has_sphagnum_moss_block", has(RegiusBlocks.SPHAGNUM_MOSS_BLOCK.get()))
                .save(recipeOutput, "regius:dried_sphagnum_moss_block_from_smelting");

    }
}
