package suike.suikecherry;


import suike.suikecherry.sitem.*;
import suike.suikecherry.sblock.*;
import suike.suikecherry.expand.*;
import suike.suikecherry.recipe.*;
import suike.suikecherry.config.*;
import suike.suikecherry.suikecherry.Tags;
import suike.suikecherry.world.CherryBiome;
import suike.suikecherry.proxy.CommonProxy;
import suike.suikecherry.oredictionary.OreDict;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class SuiKe {

    public static final String CLIENT_PROXY = "suike.suikecherry.proxy.ClientProxy";
    public static final String COMMON_PROXY = "suike.suikecherry.proxy.CommonProxy";
    //seed  -273158384
    //pos   /tp -1299 90 222
    //      -6 18

    public static boolean server = true;
    public static boolean isZhCn;

    @Mod.Instance
    public static SuiKe instance;

    @SidedProxy(clientSide = SuiKe.CLIENT_PROXY, serverSide = COMMON_PROXY)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void Construction(FMLConstructionEvent event) {
        if (FMLCommonHandler.instance().getSide().isClient()) {
            server = false;
        }

        /*检查联动模组*/Examine.examine();

        /*获取配置文件位置*/Config.config();
    }

    @Mod.EventHandler
    public static void PreInit(FMLPreInitializationEvent event) {
        if (!server) {
            String language = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
            isZhCn = (language.equals("zh_cn") || language.equals("zh_tw") || language.equals("zh_hk"));

            模组简介(event);
        }

        /*读取配置文件*/Config.configRead();
    }

    @Mod.EventHandler
    public static void PostInit(FMLPostInitializationEvent event) {
        /*矿词*/OreDict.oreDict();

        /*工作台配方*/CraftRecipe.register();
        /*熔炉配方*/FurnaceRecipe.register();

        /*执行联动*/Expand.expand();

        /*设置物品对应的方块*/ItemBase.setItemBlock();
        /*设置方块对应的物品*/BlockBase.setBlockItem();

        if (ConfigValue.spawnCherryBiome)
            CherryBiome.register();

        /*修改forge配置*/net.minecraftforge.common.ForgeModContainer.logCascadingWorldGeneration = false;
    }

    private static void 模组简介(FMLPreInitializationEvent event) {
        event.getModMetadata().autogenerated = false;
        event.getModMetadata().name = "§e" + Tags.MOD_NAME;
        event.getModMetadata().url = "";
        event.getModMetadata().updateUrl = "";
        event.getModMetadata().logoFile = "assets/suikecherry/textures/gui/ico.png";

        if (SuiKe.isZhCn) {
            event.getModMetadata().name = "§e1.12.2的樱花";
            event.getModMetadata().credits = "\n"
                    + "--------------------------------------------------" + "\n"
                    + "§d模组作者: sui_ke" + "\n"
                    + "--------------------------------------------------" + "\n"
                    + "§e相关链接:" + "\n"
                    + "§3MC百科: https://www.mcmod.cn/class/16834.html" + "\n"
                    + "§aModrinth: https://modrinth.com/mod/cherry_on_1.12.2" + "\n"
                    + "§6CurseForge: https://www.curseforge.com/minecraft/mc-mods/cherry-on-1-12-2" + "\n"
                    + "§a更新日志: https://modrinth.com/mod/cherry_on_1.12.2/changelog" + "\n"
                    + "--------------------------------------------------" + "\n"
                    + "§c本模组只发布在 \"MC百科\" , \"curseforge\" 和 \"Modrinth\" ," + "\n"
                    + "§c其他网站上的文件出问题后果自负!" + "\n"
                    + "--------------------------------------------------" + "\n";
        } else {
            event.getModMetadata().name = "§eCherry_on_1.12.2";
            event.getModMetadata().credits = "\n"
                    + "--------------------------------------------------" + "\n"
                    + "§dMod author: sui_ke" + "\n"
                    + "--------------------------------------------------" + "\n"
                    + "§eRelated links:" + "\n"
                    + "§3MC百科: https://www.mcmod.cn/class/16834.html" + "\n"
                    + "§aModrinth: https://modrinth.com/mod/cherry_on_1.12.2" + "\n"
                    + "§6CurseForge: https://www.curseforge.com/minecraft/mc-mods/cherry-on-1-12-2" + "\n"
                    + "§aChangelog: https://modrinth.com/mod/cherry_on_1.12.2/changelog" + "\n"
                    + "--------------------------------------------------" + "\n"
                    + "§cThis mod is only released on \"MC百科\" , \"curseforge\" and \"Modrinth\"," + "\n"
                    + "§cFiles on other websites are not necessarily safe!" + "\n"
                    + "§cIf something goes wrong, you will be at your own risk!" + "\n"
                    + "--------------------------------------------------" + "\n";
        }
    }
}