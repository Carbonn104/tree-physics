package com.farcr.treephysics.data;

import com.farcr.treephysics.TreePhysics;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.HashMap;
import java.util.Map;

public class TreePhysicsLang extends LanguageProvider {
    public static final Map<String, String> LANG = new HashMap<>();

    public TreePhysicsLang(PackOutput output) {
        super(output, TreePhysics.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add("death.attack.tree", "%s was crushed by a tree");
        this.add("commands.treephysics.untree.success", "Unset %s tree%s");
        this.add("treephysics.subtitles.sub_level.tree.creak", "Tree creaks");
        this.add("treephysics.subtitles.sub_level.tree.impact", "Tree crashes");
        this.add("treephysics.subtitles.sub_level.tree.leaf_rustle", "Leaves rustle");

        this.add("treephysics.config.title", "Tree Physics Config");
        this.add("treephysics.config.section.physics", "Physics");

        LANG.forEach(this::add);
    }
}
