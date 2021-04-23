package me.zombie_striker.jretrowave3d.utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderUtils {
	private ShaderUtils(){

	}

	public static int load(String vertexpath, String fragpath){
		String vert = FileUtils.loadAsString(vertexpath);
		String frag = FileUtils.loadAsString(fragpath);

		return create(vert,frag);
	}

	public static int create(String vert, String frag){
		int program = glCreateProgram();
		int vertid = glCreateShader(GL_VERTEX_SHADER);
		int fragid = glCreateShader(GL_FRAGMENT_SHADER);

		glShaderSource(vertid,vert);
		glShaderSource(fragid,frag);
		glCompileShader(vertid);
		if(glGetShaderi(vertid,GL_COMPILE_STATUS)==GL_FALSE){
			System.out.println("Failed to get shader");
			System.out.println(glGetShaderInfoLog(vertid));
			return -1;
		}
		glCompileShader(fragid);
		if(glGetShaderi(fragid,GL_COMPILE_STATUS)==GL_FALSE){
			System.out.println("Failed to get frag");
			System.out.println(glGetShaderInfoLog(fragid));
			return -1;
		}
		glAttachShader(program,vertid);
		glAttachShader(program,fragid);
		glLinkProgram(program);
		glValidateProgram(program);

		glDeleteShader(vertid);
		glDeleteShader(fragid);

		return program;
	}
}
