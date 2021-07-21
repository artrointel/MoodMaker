#version 320 es
// Inspired from https://www.shadertoy.com/view/4sXfDf
precision highp float;

uniform float uTime;

in vec2 vUv;

out vec4 FragColor;

void main()
{
    vec2 g = vUv + vec2( cos((vUv.y + uTime) / 3.0) , vUv.x/9.0);
    FragColor += 2.0/ length(vUv - g + 1e2 * cos( g + ceil(g)) ) - FragColor;
}