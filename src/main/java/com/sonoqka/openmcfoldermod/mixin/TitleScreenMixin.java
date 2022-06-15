package com.sonoqka.openmcfoldermod.mixin;

import com.sonoqka.openmcfoldermod.OpenMcFolder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    private static final Identifier MINECRAFT_FOLDER_ICON_TEXTURE = new Identifier(OpenMcFolder.MOD_ID,"textures/gui/button_mcfolder.png");

    protected TitleScreenMixin(Text title, File file) {
        super(title);
    }
    int l = this.height / 4 + 48;

    @Inject(at = @At("RETURN"), method = "initWidgetsNormal")
    private void AddCustomButton(int y, int spacingY, CallbackInfo ci) {
        this.addDrawableChild(new TexturedButtonWidget(this.width / 2 - 100 + 205, y + 12, 20, 20, 0, 0, 20, MINECRAFT_FOLDER_ICON_TEXTURE, 32, 64, (button) -> {
            String username = System.getProperty("user.name");
            if (System.getProperty("os.name").contains("Windows")) {
                Util.getOperatingSystem().open(new File("C:\\Users\\" + username + "\\AppData\\Roaming\\.minecraft"));
            } else if (System.getProperty("os.name").contains("Mac")) {
                Util.getOperatingSystem().open(new File("/Users/" + username + "/Library/Application Support/minecraft"));
            }
        }, Text.translatable("menu.button_mod_folder")));
    }
}
