#version 320 es
precision highp float;

uniform mat4 modelMatrix;

in vec3 aPos;
in vec3 aColor;

out vec4 vColor;

void main() {
    gl_Position = modelMatrix * vec4(aPos, 1.0);
    vColor = vec4(aColor, 1.0);
}





