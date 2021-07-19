#version 320 es
precision highp float;

uniform mat3 modelMatrix;

in vec3 aPos;
in vec3 aColor;

out vec4 vColor;

void main() {
    gl_Position = vec4(modelMatrix * aPos, 1.0);
    vColor = vec4(aColor, 1.0);
}





