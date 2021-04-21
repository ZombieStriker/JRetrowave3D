package me.zombie_striker.jretrowave3d.rotation;

import me.zombie_striker.jretrowave3d.data.Vector3D;

public class KernelRotatePitchYaw extends KernelRotate {


	public KernelRotatePitchYaw(RotatableWrapper wrapper) {
		this.x = wrapper.getX();
		this.y = wrapper.getY();
		this.z = wrapper.getZ();
		this.xc = wrapper.getXC();
		this.yc = wrapper.getYC();
		this.zc = wrapper.getZC();
		this.pitch = wrapper.getPitch();
		this.yaw =  wrapper.getYaw();
		this.result_x=new float[x.length];
		this.result_y=new float[y.length];
		this.result_z=new float[z.length];
	}

	@Override
	public void run() {
		int id = getGlobalId();
		float sinyaw = sin(yaw[id]);
		float sinpitch = sin(pitch[id]);
		float cosyaw = cos(yaw[id]);
		float cospitch = cos(pitch[id]);

		float x1 = x[id] - xc[id];
		float y1 = y[id] - yc[id];
		float z1 = z[id] - zc[id];

		float y3 = yc[id]+((y1*cospitch)+(sinpitch*z1));
		float z3 = zc[id]+((z1*cospitch)-(sinpitch*y1));

		float x2 = xc[id]+((cosyaw*x1)-(sinyaw*z3));
		float zchange = ((cosyaw*z3)+(sinyaw*x1));

		result_x[id] = x2;
		result_y[id] = y3;
		result_z[id] = zchange;
	}
}
