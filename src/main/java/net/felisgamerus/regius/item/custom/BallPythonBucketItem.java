package net.felisgamerus.regius.item.custom;

import com.mojang.serialization.MapCodec;
import net.felisgamerus.regius.entity.custom.genetics.LocusMap;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;

public class BallPythonBucketItem extends BucketItem {
    //private static final MapCodec<TropicalFish.Variant> VARIANT_FIELD_CODEC;
    private final EntityType<?> type;
    private final SoundEvent emptySound;

    public BallPythonBucketItem(EntityType<?> type, Fluid content, SoundEvent emptySound, Item.Properties properties) {
        super(content, properties);
        this.type = type;
        this.emptySound = emptySound;
    }

    //Turns out you're not supposed to be able to empty an empty bucket
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(
                level, player, this.content == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE
        );
        if (blockhitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            BlockPos blockpos = blockhitresult.getBlockPos();
            Direction direction = blockhitresult.getDirection();
            BlockPos blockpos1 = blockpos.relative(direction);
            if (!level.mayInteract(player, blockpos) || !player.mayUseItemAt(blockpos1, direction, itemstack)) {
                return InteractionResultHolder.fail(itemstack);
            } else {
                BlockState blockstate = level.getBlockState(blockpos);
                BlockPos blockpos2 = canBlockContainFluid(player, level, blockpos, blockstate) ? blockpos : blockpos1;
                if (this.type != null) {
                    this.checkExtraContent(player, level, itemstack, blockpos2);
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos2, itemstack);
                    }

                    this.playEmptySound(player, level, blockpos);
                    player.awardStat(Stats.ITEM_USED.get(this));
                    ItemStack itemstack1 = ItemUtils.createFilledResult(itemstack, player, getEmptySuccessItem(itemstack, player));
                    return InteractionResultHolder.sidedSuccess(itemstack1, level.isClientSide());
                } else {
                    return InteractionResultHolder.fail(itemstack);
                }
            }
        }
    }

    public void checkExtraContent(@Nullable Player player, Level level, ItemStack containerStack, BlockPos pos) {
        if (level instanceof ServerLevel) {
            this.spawn((ServerLevel)level, containerStack, pos);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }

    }

    protected void playEmptySound(@Nullable Player player, LevelAccessor level, BlockPos pos) {
        level.playSound(player, pos, this.emptySound, SoundSource.NEUTRAL, 1.0F, 1.0F);
    }

    private void spawn(ServerLevel serverLevel, ItemStack bucketedMobStack, BlockPos pos) {
        Entity entity = this.type.spawn(serverLevel, bucketedMobStack, (Player)null, pos, MobSpawnType.BUCKET, true, false);
        if (entity instanceof Bucketable bucketable) {
            CustomData customdata = (CustomData)bucketedMobStack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
            bucketable.loadFromBucketTag(customdata.copyTag());
            bucketable.setFromBucket(true);
        }

    }

    /*private EntityType<?> entityType() {
        return this.entityType.get();
    }*/

    //Unused -- Add later when ported to NeoForge
    /*public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        System.out.println("appendHoverText called by " + pStack.hashCode());
        if (this.entityType() == ModEntities.BALL_PYTHON.get()) {
            System.out.println("appending hover text...");
            CompoundTag compoundtag = pStack.getTag();
            //System.out.println("compoundtag tagType: " + compoundtag.getTagType("Genotype"));
            if (compoundtag != null && compoundtag.contains("Genotype", 3)) {
                System.out.println("Getting phenotype...");
                String genotype = compoundtag.getString("Genotype");
                ChatFormatting[] phenotypeFormat = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
                MutableComponent phenotypeList = convertGenotypeToPhenotype(genotype);

                phenotypeList.withStyle(phenotypeFormat);
                pTooltipComponents.add(phenotypeList);
            } else {
                ChatFormatting[] testFormat = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
                MutableComponent testComponent = Component.translatable("test");
                testComponent.withStyle(testFormat);
                pTooltipComponents.add(testComponent);
            }
        }
    }

    public MutableComponent convertGenotypeToPhenotype(String genotype) {
        System.out.println("genoToPheno called.");
        MutableComponent phenotype = Component.translatable("normal");
        ArrayList<String> traitList = new ArrayList<>(Arrays.asList(genotype.split("_")));
        for (int i = 0; i < traitList.size(); i++) {
            String trait = traitList.get(i);
            if (trait.endsWith(".het")) { //Checks if the trait is het recessive. True = skip
                trait = null;
            } else if (trait.endsWith(".super")) { //Checks if the trait is homo dominant. True = no longer super
                String locus = trait.substring(0, (trait.length() - 6));
                if (this.geneReference.getLocusType(locus).equals("dominant")) {
                    trait = locus;
                }
            }
            if (trait != null) {
                if (phenotype.equals("normal")) {
                    phenotype = Component.translatable(trait);
                } else {
                    phenotype.append(", ").append(Component.translatable(trait));
                }
            }
        } return phenotype;
    }*/
}
