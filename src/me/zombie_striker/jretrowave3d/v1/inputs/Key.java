package me.zombie_striker.jretrowave3d.v1.inputs;

import static org.lwjgl.glfw.GLFW.*;

public enum Key {
	W(GLFW_KEY_W),
	A(GLFW_KEY_A),
	S(GLFW_KEY_S),
	D(GLFW_KEY_D),
	UP(GLFW_KEY_UP),
	DOWN(GLFW_KEY_DOWN),
	LEFT(GLFW_KEY_LEFT),
	RIGHT(GLFW_KEY_RIGHT),
	P(GLFW_KEY_P),
	Q(GLFW_KEY_Q),
	R(GLFW_KEY_R),
	Z(GLFW_KEY_Z),
	X(GLFW_KEY_X),
	C(GLFW_KEY_C),
	V(GLFW_KEY_V),
	KEY_1(GLFW_KEY_1),
	KEY_2(GLFW_KEY_2),
	KEY_3(GLFW_KEY_3),
	KEY_4(GLFW_KEY_4),
	KEY_5(GLFW_KEY_5),
	KEY_6(GLFW_KEY_6),
	KEY_7(GLFW_KEY_7),
	KEY_8(GLFW_KEY_8),
	KEY_9(GLFW_KEY_9),
	KEY_0(GLFW_KEY_0),
	ESCAPE(GLFW_KEY_ESCAPE),

	LSHIFT(GLFW_KEY_LEFT_SHIFT),

	SPACE(GLFW_KEY_SPACE),
	E(GLFW_KEY_E),
	T(GLFW_KEY_T),
	Y(GLFW_KEY_Y),
	U(GLFW_KEY_U),
	I(GLFW_KEY_I),
	O(GLFW_KEY_O),
	F(GLFW_KEY_F),
	G(GLFW_KEY_H),
	H(GLFW_KEY_H),
	J(GLFW_KEY_J),
	K(GLFW_KEY_K),
	L(GLFW_KEY_L),
	B(GLFW_KEY_L),
	N(GLFW_KEY_L),
	M(GLFW_KEY_L),
	PERIOD(GLFW_KEY_PERIOD),
	COMMA(GLFW_KEY_COMMA),
	MINUS(GLFW_KEY_MINUS),
	EQUALS(GLFW_KEY_EQUAL),
	;

	private int keycode;

	private Key(int keycode) {
		this.keycode = keycode;
	}

	public static Key getKey(int keycode) {
		for (Key k : values()) {
			if (k.getKeyCode() == keycode)
				return k;
		}
		return null;
	}

	public int getKeyCode() {
		return keycode;
	}

}
