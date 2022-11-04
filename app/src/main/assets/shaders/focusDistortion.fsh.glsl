#version 320 es
precision highp float;

uniform sampler2D tex;
uniform vec2 uResolution;
uniform float uRadius;
uniform float uDepth;
uniform vec2 uFocus;

in vec2 vUv;
out vec4 FragColor;

// Referenced from https://www.shadertoy.com/view/llsSz7
void main() {
    float radius = uRadius;
    float depth = uDepth;
    vec2 center = uFocus;

    vec2 uv = vUv; // norm uv [0,1]
    float aspect = uResolution.x / uResolution.y;

    float ax = (uv.x - center.x) * (uv.x - center.x) +
                (uv.y - center.y) * (uv.y - center.y) / (aspect * aspect);
    ax *= 30.0f;

    float dx = -depth / radius * ax + depth / (radius * radius) * ax * ax;
    float f = ax + dx;
    if (ax > radius) f = ax;

    vec2 magnifierArea = center + (uv - center) * f / ax;

    FragColor = texture(tex, vec2(1, -1) * magnifierArea);
}
