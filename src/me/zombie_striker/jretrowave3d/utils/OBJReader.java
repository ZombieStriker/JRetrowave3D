package me.zombie_striker.jretrowave3d.utils;

import me.zombie_striker.jretrowave3d.data.Vector3D;
import me.zombie_striker.jretrowave3d.geometry.RenderableObject;
import me.zombie_striker.jretrowave3d.geometry.shapes.Shape;

import java.awt.image.ColorConvertOp;
import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OBJReader {
    public static RenderableObject renderObject(InputStream inputStream, Color color) throws IOException {
        return renderObject(inputStream, -1, -1, -1, color, false);
    }

    public static RenderableObject renderObject(InputStream inputStream, Color color, float size) throws IOException {
        return renderObject(inputStream, size, size, size, color, false);
    }

    public static RenderableObject renderObject(InputStream inputStream, float width, float height, float length, Color color, boolean scaleSidesIndividually) throws IOException {
        String[] parsed = parseFile(inputStream);
        List<Vector3D> vertexes = new ArrayList<>();
        float maxWidth = 0;
        float maxHeight = 0;
        float maxlength = 0;
        float maxScale = 0;
        for (String s : parsed) {
            if (s.startsWith("v  ")) {
                String[] parts = s.split("v  ")[1].split(" ");
                float x = Float.parseFloat(parts[0]);
                float y = Float.parseFloat(parts[1]);
                float z = Float.parseFloat(parts[2]);
                vertexes.add(new Vector3D(x, y, z));
                if (maxWidth < x)
                    maxWidth = x;
                if (maxHeight < y)
                    maxHeight = y;
                if (maxlength < z)
                    maxlength = z;

                if (maxScale < x)
                    maxScale = x;
                if (maxScale < y)
                    maxScale = y;
                if (maxScale < z)
                    maxScale = z;
            } else if (s.startsWith("v ")) {
                String[] parts = s.split(" ");
                float x = Float.parseFloat(parts[1]);
                float y = Float.parseFloat(parts[2]);
                float z = Float.parseFloat(parts[3]);
                vertexes.add(new Vector3D(x, y, z));
                if (maxWidth < x)
                    maxWidth = x;
                if (maxHeight < y)
                    maxHeight = y;
                if (maxlength < z)
                    maxlength = z;


                if (maxScale < x)
                    maxScale = x;
                if (maxScale < y)
                    maxScale = y;
                if (maxScale < z)
                    maxScale = z;

            }
        }
        if (width != -1) {
            for (Vector3D v : vertexes) {
                if (scaleSidesIndividually) {
                    v.setX(v.getX() / maxWidth * width);
                } else {
                    v.setX(v.getX() / maxScale * width);
                }
            }
        }
        if (height != -1) {
            for (Vector3D v : vertexes) {
                if (scaleSidesIndividually) {
                    v.setY(v.getY() / maxHeight * height);
                } else {
                    v.setY(v.getY() / maxScale * height);

                }
            }
        }
        if (length != -1) {
            for (Vector3D v : vertexes) {
                if (scaleSidesIndividually) {
                    v.setZ(v.getZ() / maxlength * length);
                } else {
                    v.setZ(v.getZ() / maxScale * length);
                }
            }
        }
        Shape shape = new Shape(vertexes.toArray(new Vector3D[vertexes.size()]));
        String shapename = "";
        int sides = 0;
        for (String s : parsed) {
            if (s.startsWith("o ")) {
                shapename = s.substring(2);
            } else if (s.startsWith("f ")) {
                String[] parts = s.split(" ");
                if (parts.length == 4) { // triangle. 3 Parts
                    String part1 = parts[1];
                    String part2 = parts[2];
                    String part3 = parts[3];
                    String[] slashes1 = part1.split("/");// x
                    String[] slashes2 = part2.split("/");// z
                    String[] slashes3 = part3.split("/");// y
                    int x = Integer.parseInt(slashes1[0]) - 1;
                    int y = Integer.parseInt(slashes2[0]) - 1;
                    int z = Integer.parseInt(slashes3[0]) - 1;
                    shape.registerTriangle(x, y, z, color, shapename);
                } else if (parts.length == 5) { //plane, 4 parts

                    String part1 = parts[1];
                    String part2 = parts[2];
                    String part3 = parts[3];
                    String part4 = parts[4];
                    String[] slashes1 = part1.split("/");// x
                    String[] slashes2 = part2.split("/");// z
                    String[] slashes3 = part3.split("/");// y
                    String[] slashes4 = part4.split("/");// other side of plane
                    int x = Integer.parseInt(slashes1[0]) - 1;
                    int y = Integer.parseInt(slashes2[0]) - 1;
                    int z = Integer.parseInt(slashes3[0]) - 1;
                    shape.registerTriangle(x, z, y, color,shapename);
                    if (slashes4[0].trim().length() >= 1) {
                        int otherside = Integer.parseInt(slashes4[0]) - 1;
                        shape.registerTriangle(x, otherside, z, color, shapename);
                    }
                    //if(sides==0)
                    //	return shape;
                    sides++;
                }
                //for (int i = 1; i < parts.length; i++) {
                //}
            }
        }
        return shape;
    }


    public static String[] parseFile(File file) throws FileNotFoundException {
        try {
            FileReader reader = new FileReader(file);
            StringBuilder sb = new StringBuilder();
            int character = -1;
            while ((character = reader.read()) >= 0) {
                sb.append((char) character);
            }
            return sb.toString().split("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] parseFile(InputStream is) throws IOException {
        InputStreamReader reader = new InputStreamReader(is);
        StringBuilder sb = new StringBuilder();
        int character = -1;
        while ((character = reader.read()) >= 0) {
            sb.append((char) character);
        }
        return sb.toString().split("\n");
    }
}
