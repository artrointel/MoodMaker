#version 320 es
precision highp float;

uniform sampler2D tex;

in vec2 vUv;
out vec4 FragColor;

void main() {
    FragColor = texture(tex, vUv);
}
