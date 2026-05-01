package com.farcr.treephysics.index;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static com.farcr.treephysics.TreePhysics.path;

public class TreePhysicsTags {
    public static final TagKey<Block> STAYS_ON_TREE = create("stays_on_tree");
    public static final TagKey<Block> PRODUCES_DUST_ON_IMPACT = create("produces_dust_on_impact");

    private static TagKey<Block> create(String id) {
        return TagKey.create(Registries.BLOCK, path(id));
    }
}
