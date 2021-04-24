package me.zombie_striker.jretrowave3d.geometry.shapes;

import me.zombie_striker.jretrowave3d.World;
import me.zombie_striker.jretrowave3d.data.Triangle;
import me.zombie_striker.jretrowave3d.data.Vector3D;
import me.zombie_striker.jretrowave3d.geometry.RenderableObject;
import me.zombie_striker.jretrowave3d.utils.MathUtil;

import java.awt.*;
import java.awt.event.WindowStateListener;
import java.util.*;
import java.util.List;

public class Shape extends RenderableObject {


    private Vector3D[] vertexes;
    private HashMap<String, Triangle[]> shapeNames = new HashMap<>();
    private Set<String> hiddenSides = new HashSet<>();
    private Triangle[] cachedVisable = null;
    private Vector3D minLocation;
    private Vector3D maxLocation;
    private float yaw = 0;
    private float pitch = 0;

    public RenderableObject clone() {
        Shape shape = new Shape(vertexes);
        Triangle[] tt = new Triangle[getTriangles().length];
        for (int i = 0; i < getTriangles().length; i++) {
            if (getTriangles()[i] != null)
                tt[i] = new Triangle(getTriangles()[i].getTrues()[0].clone(), getTriangles()[i].getTrues()[1].clone(), getTriangles()[i].getTrues()[2].clone(), getTriangles()[i].getColor());
        }
        shape.setTriangles(tt);
        shape.minLocation = new Vector3D(minLocation);
        shape.maxLocation = new Vector3D(maxLocation);
        shape.hiddenSides = new HashSet<>(hiddenSides);
        shape.cachedVisable = cachedVisable;
        shape.shapeNames = new HashMap<>(shapeNames);
        return shape;
    }


    public Shape(Vector3D... vertexes) {
        super(0);
        this.vertexes = vertexes;
        minLocation = MathUtil.getMinVector(true, vertexes).clone();
        maxLocation = MathUtil.getMaxVector(true, vertexes).clone();
        setUpdateTrianglesWithGPU();
    }

    public Set<String> getHiddenSides(){
        return hiddenSides;
    }
    public Set<String> getShapes(){
        return shapeNames.keySet();
    }

    private void addTriangle(String name, Triangle t) {
        cachedVisable=null;
        Triangle[] oldarray = shapeNames.get(name);
        if (oldarray == null) {
            oldarray = new Triangle[1];
            oldarray[0] = t;
            shapeNames.put(name, oldarray);
            return;
        }
        Triangle[] newarray = new Triangle[oldarray.length + 1];
        for (int i = 0; i < oldarray.length; i++) {
            newarray[i] = oldarray[i];
        }
        newarray[newarray.length - 1] = t;
        shapeNames.put(name, newarray);

    }

    public void registerTriangle(int v1, int v2, int v3, Color c, String shapename) {
        Triangle[] t = new Triangle[getTriangles().length + 1];
        for (int i = 0; i < getTriangles().length; i++) {
            t[i] = getTriangles()[i];
        }
        try {
            t[t.length - 1] = new Triangle(vertexes[v1].clone()/*.add(minLocation.getX(),minLocation.getY(),minLocation.getZ())*/,
                    vertexes[v2].clone()/*.add(minLocation.getX(),minLocation.getY(),minLocation.getZ())*/,
                    vertexes[v3].clone()/*.add(minLocation.getX(),minLocation.getY(),minLocation.getZ())*/, c);
        } catch (Error | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        setTriangles(t);
        addTriangle(shapename, t[t.length - 1]);
        //updateTriangles();
    }

    public void registerTriangle(int v1, int v2, int v3) {
        Triangle[] t = new Triangle[getTriangles().length + 1];
        for (int i = 0; i < getTriangles().length; i++) {
            t[i] = getTriangles()[i];
        }
        try {
            t[t.length - 1] = new Triangle(vertexes[v1].clone().add(minLocation.getX(), minLocation.getY(), minLocation.getZ()),
                    vertexes[v2].clone().add(minLocation.getX(), minLocation.getY(), minLocation.getZ()),
                    vertexes[v3].clone().add(minLocation.getX(), minLocation.getY(), minLocation.getZ()), new Color(200, 200, 200));
        } catch (Error | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        setTriangles(t);
        //updateTriangles();
    }

    public void registerTriangle(Vector3D v1, Vector3D v2, Vector3D v3) {
        Triangle[] t = new Triangle[getTriangles().length + 1];
        for (int i = 0; i < getTriangles().length; i++) {
            t[i] = getTriangles()[i];
        }
        t[t.length - 1] = new Triangle(v1, v2, v3, new Color(200, 200, 200));
        setTriangles(t);
    }

    @Override
    public Vector3D getLocation() {
        return minLocation;
    }

    @Override
    public Triangle[] getTrianglesForRendering(World world) {
        if (cachedVisable != null)
            return cachedVisable;
        if (hiddenSides.size() == 0)
            return getTriangles();
        List<Triangle> array = new ArrayList<>();
        for (Map.Entry<String, Triangle[]> e : shapeNames.entrySet()) {
            if (!hiddenSides.contains(e.getKey())) {
                for (Triangle t : e.getValue())
                    array.add(t);
            }
        }
        return cachedVisable = array.toArray(new Triangle[array.size()]);

    }

    @Override
    public Triangle[] getAllTriangles(World world) {
        return getTriangles();
    }

    @Override
    public boolean isInside(Vector3D location, float size) {
        return false;
    }

    @Override
    public Vector3D getCenter(World world) {
        Vector3D offset = new Vector3D(maxLocation.getX() - minLocation.getX(), maxLocation.getY() - minLocation.getY(), maxLocation.getZ() - minLocation.getZ());
        return minLocation.clone().add(offset);
        //return minLocation.clone();
    }

    @Override
    public float getYaw() {
        return yaw;
    }

    @Override
    public void setYawRadians(float yaw) {
        this.yaw = yaw;
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    @Override
    public void setPitchRadians(float pitch) {
        this.pitch = pitch;
    }

    public void setHidden(String name){
        this.hiddenSides.add(name);
        cachedVisable=null;
    }

    @Override
    public void teleport(Vector3D location) {
        Vector3D offset = new Vector3D(location.getX() - minLocation.getX(), location.getY() - minLocation.getY(), location.getZ() - minLocation.getZ());
        shiftTriangles(offset);
        minLocation.add(offset);
        maxLocation.add(offset);
    }

    @Override
    public RenderableObject setSize(float width, float height, float length) {
        resize(maxLocation, new Vector3D(minLocation).add(width, height, length));
        maxLocation = new Vector3D(minLocation).add(width, height, length);
        return this;
    }

    @Override
    public RenderableObject setSize(float resize) {
        float xoffset = maxLocation.getX() - minLocation.getX();
        float yoffset = maxLocation.getY() - minLocation.getY();
        float zoffset = maxLocation.getZ() - minLocation.getZ();
        xoffset *= resize;
        yoffset *= resize;
        zoffset *= resize;
        Vector3D newMax = new Vector3D(minLocation).add(xoffset, yoffset, zoffset);
        resize(maxLocation, newMax);
        maxLocation = newMax;
        return this;
    }

    @Override
    public void updateTriangles() {
        super.updateTriangles();
        this.cachedVisable = null;
    }

    /*public void updateTriangles(Vector3D newLocation) {
		Vector3D offset = new Vector3D(newLocation);
		offset.subtract(minLocation);
		shiftTriangles(offset);
		super.updateTriangles();
	}*/
}
