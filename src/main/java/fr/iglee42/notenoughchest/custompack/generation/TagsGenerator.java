package fr.iglee42.notenoughchest.custompack.generation;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.iglee42.notenoughchest.NotEnoughChest;
import fr.iglee42.notenoughchest.custompack.PathConstant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static fr.iglee42.notenoughchest.NotEnoughChest.MODID;

public class TagsGenerator {

    private static List<String> chests = new ArrayList<>();
    public static void generate() {

        NotEnoughChest.WOOD_TYPES.forEach(wt->{
            if (!wt.getNamespace().equals("minecraft")) {
                String t = wt.getPath();
                chests.add(MODID+":"+t+"_chest");
            }
        });

        try {
            JsonObject tag = new JsonObject();
            tag.addProperty("replace",false);
            JsonArray chest = new JsonArray();
            chests.forEach(chest::add);
            tag.add("values",chest);

            writeChestTag(tag,new File(PathConstant.FORGE_BLOCK_TAGS_PATH.toFile(), "chests.json"));
            writeChestTag(tag,new File(PathConstant.FORGE_BLOCK_TAGS_PATH.toFile()+"/chests", "wooden.json"));
            writeChestTag(tag,new File(PathConstant.FORGE_ITEM_TAGS_PATH.toFile(), "chests.json"));
            writeChestTag(tag,new File(PathConstant.MC_MINEABLE_TAGS_PATH.toFile(), "axe.json"));

        } catch (Exception exception){
            NotEnoughChest.LOGGER.error("An error was detected when tags generating",exception);
        }
    }

    private static void writeChestTag(JsonObject tag, File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(tag));
        writer.close();
    }

}
