package singleton;

import com.badlogic.gdx.math.Vector2;

public class PhysicsTool {
    /**
     * standardize the strength
     * @param dir
     * @param strength
     * @return
     */
    public static Vector2 standard_strength(Vector2 dir,int strength){
        float s=(float) Math.sqrt(dir.x*dir.x+dir.y*dir.y);
        if (Math.abs(s)<.0001f)s=1;
        return new Vector2(50*strength*dir.x/s,50*strength*dir.y/s);
    }
}
