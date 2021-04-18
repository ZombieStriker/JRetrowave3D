package me.zombie_striker.jretrowave3d.data;

public enum TextureStitchingAlgo {

	DEFAULT(0,1, 2),
	DEFAULT_BOTTOM(2,1, 0),

	;

	int index1;
	int index2;
	int index3;

	TextureStitchingAlgo(int i , int j, int k){
		this.index1 = i;
		this.index2 = j;
		this.index3 = k;
	}

	public int getIndex1(){
		return index1;
	}
	public int getIndex2(){
		return index2;
	}
	public int getIndex3(){
		return index3;
	}
}
