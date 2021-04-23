#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
vec2 tc;
} fs_in;

uniform vec3 texc;

void main()
{
color = vec4(0.1,0.1,0.4,1.0);//texture(tex,fs_in.tc);\n" +
//color = texture(tex,fs_in.tc);\n" +
}