#version 320 es
precision highp float;

in vec2 vPosHalfNDC;
in vec4 vColor;
out vec4 FragColor;

void main() {
    float circleAlpha = 1.0 - step(0.5, length(vPosHalfNDC));
    FragColor = mix(vec4(0.0, 0.0, 0.0, 0.0), vColor, circleAlpha);
}
