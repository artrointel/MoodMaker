#version 320 es
precision highp float;

uniform mat4 projMatrix;
uniform mat4 modelMatrix;

in vec3 aPos;
in vec2 aUv;

out vec2 vUv;

void main() {
    gl_Position = projMatrix * modelMatrix * vec4(aPos, 1.0);
    vUv = aUv;
}





