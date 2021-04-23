package me.zombie_striker.jretrowave3d.graphics;

import me.zombie_striker.jretrowave3d.data.Vector3D;
import me.zombie_striker.jretrowave3d.utils.ShaderUtils;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

	public static final int VERTEX = 0;
	public static final int TEXTURE_COORDS = 1;
	public static final int COLOR = 2;

	public static final String FRAG_SHADER = "#version 330 core\n" +
			"\n" +
			"layout (location = 0) out vec4 color;\n" +
			"\n" +
			"in DATA\n" +
			"{\n" +
			"vec2 tc;\n" +
			"vec4 cc;\n" +
			"} fs_in;\n" +
			"\n" +
			"uniform sampler2D tex;\n" +
			"\n" +
			"void main()\n" +
			"{\n" +
			"//color = vec4(0.1,0.1,0.4,1.0);//texture(tex,fs_in.tc);\n" +
			"color = texture(tex,fs_in.tc);\n" +
			"}";
	public static final String FRAG_SHADER2 = "#version 330 core\n" +
			"\n" +
			"layout (location = 0) out vec4 color;\n" +
			"\n" +
			"in DATA\n" +
			"{\n" +
			"\tvec2 tc;\n" +
			"\tvec4 cc;\n" +
			"} fs_in;\n" +
			"\n" +
			"\n" +
			"void main()\n" +
			"{\n" +
			"\tcolor = vec4(texr,texg,texb,1.0);\n" +
			"\t//color = texture(tex,fs_in.tc);\n" +
			"}";
	public static final String FRAG_SHADER3 = "#version 410 core\n" +
			"\n" +
			"layout (location = 0) out vec4 color;\n" +
			"\n" +
			"in DATA\n" +
			"{\n" +
			"\tvec2 tc;\n" +
			"\tvec4 cc;\n" +
			"} fs_in;\n" +
			"\n" +
			"void main()\n" +
			"{\n" +
			"\tcolor = fs_in.cc;\n" +
			"\t//color = texture(tex,fs_in.tc);\n" +
			"}";
	public static final String VERTEX_SHADER = "#version 410 core\n" +
			"\n" +
			"layout (location = 0) in vec4 position;\n" +
			"layout (location = 1) in vec2 tc;\n" +
			"layout (location = 2) in vec4 colorcur;\n" +
			"\n" +
			"\n" +
			"out DATA\n" +
			"{\n" +
			" vec2 tc;\n" +
			" vec4 cc;\n" +
			"} vs_out;\n" +
			"\n" +
			"\n" +
			"void main()\n" +
			"{\n" +
			"\tgl_Position = position;\n" +
			"\tvs_out.tc = tc;\n" +
			"\tvs_out.cc = colorcur;\n" +
			"}";

	public static Shader SCREEN;
	private int id;
	private Map<String, Integer> cache = new HashMap<>();


	public Shader(String vertex, String fragment, boolean isPaths) {
		if (isPaths) {
			id = ShaderUtils.load(vertex, fragment);
		} else {
			id = ShaderUtils.create(vertex, fragment);
		}
	}

	public static void loadAll() {
		SCREEN = new Shader(VERTEX_SHADER, FRAG_SHADER, false);
	}

	public int getUniform(String name) {
		if (cache.containsKey(name)) {
			return cache.get(name);
		}
		int i = glGetUniformLocation(id, name);
		if (i == -1) {
			System.out.println("Uniform is -1 for \"" + name + "\" for ID="+id+".");
		}else {
			cache.put(name, i);
		}
		return i;
	}

	public void setUniform1i(String name, int value) {
		glUniform1i(getUniform(name), value);
	}

	public void setUniform1f(String name, float value) {
		glUniform1f(getUniform(name), value);
	}

	public void setUniform2f(String name, float x, float y) {
		glUniform2f(getUniform(name), x, y);
	}

	public void setUniform3f(String name, float x, float y, float z) {
		glUniform3f(getUniform(name), x, y, z);
	}

	public void setUniform3f(String name, Vector3D vector) {
		glUniform3f(getUniform(name), vector.getX(), vector.getY(), vector.getZ());
	}

	public void enable() {
		glUseProgram(id);
	}

	public void disable() {
		glUseProgram(id);
	}
}
