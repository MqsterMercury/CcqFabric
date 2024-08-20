package org.morro.ccqfabric.client.utils;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.text.Text;
import org.morro.ccqfabric.client.mixins.TextDisplayEntityAccessor;

public class TextUtils {

    public static Text getDisplayedText(DisplayEntity.TextDisplayEntity textDisplayEntity) {
        TrackedData<Text> textTrackedData = TextDisplayEntityAccessor.getTextTrackedData();
        return textDisplayEntity.getDataTracker().get(textTrackedData);
    }

}
