package net.qbismx.damagepopup.popup;

public class PopUp {

    public final int entityId;
    public final float damage;

    public int age = 0;

    public final double randX;
    public final double randZ;

    public PopUp(int entityId, float damage){
        this.entityId = entityId;
        this.damage = damage;

        this.randX = (Math.random() - 0.5) * 0.3;
        this.randZ = (Math.random() - 0.5) * 0.3;
    }
}
