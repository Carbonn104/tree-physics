package com.farcr.treephysics.event;

import com.farcr.treephysics.TreePhysics;
import com.farcr.treephysics.api.TreeUtil;
import com.farcr.treephysics.api.tree_gathering.Tree;
import com.farcr.treephysics.api.tree_gathering.TreeGatherer;
import net.createmod.catnip.outliner.Outliner;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@EventBusSubscriber(modid = TreePhysics.MOD_ID)
public class CommonEvents {

    private static final Collection<BlockPos> outline = Collections.synchronizedCollection(new HashSet<>());

    @SubscribeEvent
    public static void postLevelTick(LevelTickEvent.Post event) {
        if(!outline.isEmpty()) {
            Outliner.getInstance().showCluster("tree", outline);
        }
    }

    @SubscribeEvent
    public static void blockBreak(BlockEvent.BreakEvent event) {
        if(!event.getPlayer().isShiftKeyDown() && event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).is(ItemTags.AXES)) {
            event.setCanceled(true);

            if(!TreeGatherer.isValidTree(event.getLevel(), event.getPos())) {
                return;
            }

            Tree tree = TreeGatherer.gatherTree(event.getLevel(), event.getPos(), TreeUtil::treeSpread, TreeUtil.DIRECTION_OFFSETS, event.getPos());
            if(tree == null) {
                outline.clear();
                return;
            }

            outline.clear();
            outline.addAll(tree.blocks());
        } else {
            outline.clear();
        }
    }


}
