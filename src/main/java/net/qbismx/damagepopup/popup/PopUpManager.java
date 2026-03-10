package net.qbismx.damagepopup.popup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopUpManager {

    private static final Map<Integer, List<PopUp>> POPUPS = new HashMap<>();

    public static void add(int entityId, float damage){
      // System.out.println("PopUp added"); // 動作検証用。機能している。
        POPUPS.computeIfAbsent(entityId, id -> new ArrayList<>())
                .add(new PopUp(entityId, damage));
    }

    public static List<PopUp> get(int entityId){
        return POPUPS.get(entityId);
    }

    public static void remove(int entityId) {
        POPUPS.remove(entityId);
    }

}
