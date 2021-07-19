#version 320 es
precision highp float;

in vec2 aPos;
in vec2 aColor;

out vec4 vColor;

void main() {
    gl_Position = vec4(aPos, 0.0 ,1.0);
    vColor = vec4(aColor, 0.0, 1.0);
}





