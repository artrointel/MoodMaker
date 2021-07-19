#version 320 es
precision highp float;

in vec2 vPosNDC;
in vec4 vColor;
out vec4 FragColor;

void main() {
    float circleAlpha = 1.0 - step(1.0, length(vPosNDC));
    FragColor = mix(vec4(0.0, 0.0, 0.0, 0.0), vColor, circleAlpha);
}
