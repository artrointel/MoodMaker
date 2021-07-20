#version 320 es
precision highp float;

uniform mat4 projMatrix;
uniform mat4 modelMatrix;

in vec3 aPos; // Use Mesh.QUAD_2D to make it same as NDC Coord.
in vec3 aColor;

out vec4 vColor;
out vec2 vPosNDC;

void main() {
    gl_Position = projMatrix * modelMatrix * vec4(aPos, 1.0);
    vPosNDC = aPos.xy;
    vColor = vec4(aColor, 1.0);
}





