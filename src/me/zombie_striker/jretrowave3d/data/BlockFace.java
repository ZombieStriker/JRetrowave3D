package me.zombie_striker.jretrowave3d.data;

public enum BlockFace {
	FRONT(0),
	BACK(1),
	LEFT(2),
	RIGHT(3);

	int faceindex ;

	BlockFace(int i){
		this.faceindex = i;
	}
	public int getIndex(){
		return faceindex;
	}
}
