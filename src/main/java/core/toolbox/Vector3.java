package core.toolbox;

import org.joml.Vector3f;

public class Vector3 {

    public float x;
    public float y;
    public float z;

    public static final Vector3 ZERO = new Vector3(0,0,0);

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3f vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    public double magnitude(){
        return Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));
    }

    public Vector3 normalize(){
        double magnitude = magnitude();
        return new Vector3((float) (x/magnitude), (float) (y/magnitude), (float) (z/magnitude));
    }

    public Vector3 add(Vector3 vector){
        return new Vector3(this.x + vector.x, this.y + vector.y, this.z + vector.z);
    }

    public Vector3 sub(Vector3 term) {
        return new Vector3(this.x - term.x, this.y - term.y, this.z - term.z);
    }

    public Vector3 mul(double factor){
        return new Vector3((float) (this.x * factor), (float) (this.y * factor), (float) (this.z * factor));
    }

    public Vector3 mul(Vector3 factor){
        return new Vector3(this.x * factor.x, this.y * factor.y, this.z * factor.z);
    }

    public Vector3 divide(double divisor){
        return new Vector3((float) (this.x / divisor), (float) (this.y / divisor), (float) (this.z / divisor));
    }

    public Vector3f toJomlVector(){
        return new Vector3f((float) x, (float) y, (float) z);
    }

    public void log(){
        System.out.println("[x: " + x + "] [y: " + y + "] [z: " + z + "] [magnitude: " + magnitude() + "]");
    }
}
