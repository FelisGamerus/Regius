package net.felisgamerus.regius.util;

import net.felisgamerus.regius.Regius;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class ModTags {
    public static class Items {
        //All food ball pythons can eat
        public static final TagKey<Item> BALL_PYTHON_GENERAL_FOOD = create("ball_python_general_food");
        //All foods ball pythons can be bred with
        public static final TagKey<Item> BALL_PYTHON_BREEDABLE_FOOD = create("ball_python_breedable_food");

        private static TagKey<Item> create(String name) {
            return net.minecraft.tags.TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Regius.MOD_ID, name));
        }
    }

    public static class Entities {
        //All mobs ball pythons can hunt
        public static final TagKey<EntityType<?>> BALL_PYTHON_PREY = create("ball_python_prey");

        private static TagKey<EntityType<?>> create(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Regius.MOD_ID, name));
        }
    }
}
