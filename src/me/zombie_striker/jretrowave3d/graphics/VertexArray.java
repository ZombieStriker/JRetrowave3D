package me.zombie_striker.jretrowave3d.graphics;

import me.zombie_striker.jretrowave3d.utils.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.awt.Color;

public class VertexArray {

	private int count;
	private int vao, vbo , ibo ,tco, color;


	public VertexArray(float[] vertex, byte[] indecies, float[] textureCoords, Color color){
		count=indecies.length;

		float[] c = new float[]{(float)color.getRed()/255.0f,(float)color.getGreen()/255.0f,(float)color.getBlue()/255.0f,(float)color.getAlpha()/255.0f};

		vao = glGenVertexArrays();
		glBindVertexArray(vao);

		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER,vbo);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertex), GL_STATIC_DRAW);
		glVertexAttribPointer(Shader.VERTEX, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(Shader.VERTEX);

		tco = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER,tco);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(textureCoords), GL_STATIC_DRAW);
		glVertexAttribPointer(Shader.TEXTURE_COORDS, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(Shader.TEXTURE_COORDS);

		this.color = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER,this.color);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(c), GL_STATIC_DRAW);
		glVertexAttribPointer(Shader.COLOR, 4, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(Shader.COLOR);

		ibo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER,BufferUtils.createByteBuffer(indecies),GL_STATIC_DRAW);


		glBindBuffer(GL_ARRAY_BUFFER,0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
		glBindVertexArray(0);
	}

	public void updateVertexes(float[] newVertexes){
		bind();
		glBindBuffer(GL_ARRAY_BUFFER,vbo);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(newVertexes), GL_STATIC_DRAW);
		glVertexAttribPointer(Shader.VERTEX, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(Shader.VERTEX);
		unbind();
	}

	public void bind(){
		glBindVertexArray(vao);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ibo);
	}
	public void unbind(){
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
		glBindVertexArray(0);
	}

	public void draw(){
		glDrawElements(GL_TRIANGLES,count,GL_UNSIGNED_BYTE,0);
		//glDrawArrays(GL_TRIANGLES,0,count);
	}
	public void render(){
		bind();
		draw();
	}
}
