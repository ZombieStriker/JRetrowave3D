package me.zombie_striker.jretrowave3d.utils;

import me.zombie_striker.jretrowave3d.data.Vector3D;

public class MathUtil {

	public static double distance(Vector3D p1, Vector3D p2) {
		return Math.sqrt(distanceSquared(p1, p2));
	}

	public static double distanceSquared(Vector3D p1, Vector3D p2) {
		int dif = 0;

		int x1 = (int) (p1.getX() - p2.getX());
		int z1 = (int) (p1.getZ() - p2.getZ());
		int y1 = (int) (p1.getY() - p2.getY());

		dif += (x1) * (x1);
		dif += (z1) * (z1);
		dif += (y1) * (y1);
		return dif;
	}

	public static double[][] multiplyMatrix(double[][] mat1, double[][] mat2, int row, int column){
		double[][] newmat = new double[row][column];
		for(int row1 = 0; row1 < row;row1++){
			for(int col = 0; col < column;col++) {
				double sum = 0;
				for (int k = 0; k < mat2.length; k++) {
					double x =mat1[row1][k];
					double z= mat2[k][col];
					sum +=(x*z);
				}
				newmat[row1][col] = sum;
			}
		}
		return newmat;
	}
	public static int[] copy(int[] src){
		int[] y1 = new int[3];
		for(int i = 0; i < y1.length;i++)
		y1[i]=src[i];
		return y1;
	}

	public static Vector3D getMaxVector(boolean b, Vector3D[] vertexes) {
		Vector3D min = vertexes[0].clone();
		for(Vector3D v : vertexes){
			if(b){
				if(v.getX() > min.getX()){
					min.setX(v.getX());
				}
				if(v.getY() > min.getY()){
					min.setY(v.getY());
				}
				if(v.getZ() > min.getZ()){
					min.setZ(v.getZ());
				}
			}else{
				//TODO: Add if I want to get the Point, not absolute
			}
		}
		return min;
	}

	public static Vector3D getMinVector(boolean b, Vector3D[] vertexes) {
		Vector3D min = vertexes[0].clone();
		for(Vector3D v : vertexes){
			if(b){
				if(v.getX() < min.getX()){
					min.setX(v.getX());
				}
				if(v.getY() < min.getY()){
					min.setY(v.getY());
				}
				if(v.getZ() < min.getZ()){
					min.setZ(v.getZ());
				}
			}else{
				//TODO: Add if I want to get the Point, not absolute
			}
		}
		return min;
	}
}
