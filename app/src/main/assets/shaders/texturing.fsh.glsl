#version 320 es
precision highp float;

uniform sampler2D tex;

uniform float uAlpha;
in vec2 vUv;
out vec4 FragColor;

void main() {
    vec4 color = texture(tex, vUv);
    color.a = color.a * uAlpha;
    FragColor = color;
}
