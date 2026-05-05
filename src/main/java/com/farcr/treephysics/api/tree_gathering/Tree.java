package com.farcr.treephysics.api.tree_gathering;

import net.minecraft.core.BlockPos;

import java.util.Collection;

public record Tree(Collection<BlockPos> blocks, Collection<BlockPos> fallingBlocks, boolean hasRoot) {
}
