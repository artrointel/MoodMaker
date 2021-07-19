#version 320 es
// inspired from https://www.shadertoy.com/view/XsjGDt
precision highp float;

uniform mat3 modelMatrix;

in vec3 aPos; // Use Mesh.QUAD_2D to make it same as NDC Coord.
in vec3 aColor;

out vec4 vColor;
out vec2 vPosNDC;

void main() {
    gl_Position = vec4(modelMatrix * aPos, 1.0);
    vPosNDC = aPos.xy;
    vColor = vec4(aColor, 1.0);
}





