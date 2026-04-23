package com.farcr.treephysics.api.tree_gathering;

import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface SpreadPredicate {
    boolean test(BlockState from, BlockState to, TreeContext context);
}
