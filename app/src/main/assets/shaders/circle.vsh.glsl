#version 320 es
precision highp float;

uniform mat4 projMatrix;
uniform mat4 modelMatrix;

in vec3 aPos;
in vec3 aColor;

out vec4 vColor;
out vec2 vPosHalfNDC;

void main() {
    gl_Position = projMatrix * modelMatrix * vec4(aPos, 1.0);
    vPosHalfNDC = aPos.xy;
    vColor = vec4(aColor, 1.0);
}





