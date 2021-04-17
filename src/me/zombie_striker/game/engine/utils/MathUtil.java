package me.zombie_striker.game.engine.utils;

import me.zombie_striker.game.engine.data.Vector3D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MathUtil {

	public static double distance(Vector3D p1, Vector3D p2) {
		return Math.sqrt(distanceSquared(p1, p2));
	}

	public static double distanceSquared(Vector3D p1, Vector3D p2) {
		int dif = 0;

		int x1 = (int) (p1.getX() - p2.getX());
		int z1 = (int) (p1.getZ() - p2.getZ());

		dif += (x1) * (x1);
		dif += (z1) * (z1);
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
}
