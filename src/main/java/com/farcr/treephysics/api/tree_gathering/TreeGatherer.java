package com.farcr.treephysics.api.tree_gathering;

import com.farcr.treephysics.api.TreeUtil;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class TreeGatherer {
    // TODO: make this a config
    private static final int MAX_ITERATIONS = 100000;

    public static boolean isValidTree(BlockGetter blockGetter, BlockPos start) {
        BlockState state = blockGetter.getBlockState(start);
        if(!state.is(BlockTags.LOGS)) return false;

        Tree tree = gatherTree(blockGetter, start, TreeUtil::logSpread, TreeUtil.DOWNWARD_OFFSETS, null);
        if(tree != null) {
            BlockState belowState = blockGetter.getBlockState(tree.lowestPos().below());
            return belowState.is(Blocks.ROOTED_DIRT);
        }

        return false;
    }

    public static @Nullable Tree gatherTree(BlockGetter blockGetter, BlockPos root, SpreadPredicate predicate, BlockPos[] offsets, @Nullable BlockPos floor) {
        BlockState rootState = blockGetter.getBlockState(root);
        TreeContext context = new TreeContext();
        if(!context.isLog(rootState)) {
            return null;
        }

        BlockPos lowestPos = root;

        Set<Long> visited = new LongOpenHashSet();
        Set<BlockPos> result = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();

        queue.add(root);

        int count = 0;
        while (!queue.isEmpty() && count < MAX_ITERATIONS) {
            BlockPos centerPos = queue.poll();
            BlockState centerState = blockGetter.getBlockState(centerPos);
            visited.add(centerPos.asLong());
            result.add(centerPos);

            if(centerPos.getY() < lowestPos.getY()) {
                lowestPos = centerPos;
            }

            for (BlockPos offset : offsets) {
                BlockPos nextPos = centerPos.offset(offset);
                long nextLong = nextPos.asLong();
                if(visited.contains(nextLong)) {
                    continue;
                }

                if(floor != null && nextPos.getY() < floor.getY()) {
                    if(isValidTree(blockGetter, nextPos)) {
                        continue;
                    }
                }

                BlockState nextState = blockGetter.getBlockState(nextPos);
                if(nextState.isAir()) {
                    visited.add(nextLong);
                    continue;
                }

                if(predicate.test(centerState, nextState, context)) {
                    visited.add(nextLong);
                    queue.add(nextPos);
                }
            }

            count++;
        }

        return new Tree(result, lowestPos);
    }

}
