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

    uv = center + (uv - center) * f / ax;
    uv = vec2(1, -1) * uv;

    // do simple blur
    float normRadius = (uRadius - 1.0) * 0.25; // map uRadius[1,5] to [0,1]
    float lod = sqrt(normRadius) * 8.0 + 1.0; // map to [1,8] with sqrt interpolation
    float dUv = lod - 1.0;
    dUv *= 0.001;

    vec4 color = textureLod(tex, uv, lod);
    color += textureLod(tex, uv + dUv * vec2(1.0, 1.0), lod);
    color += textureLod(tex, uv + dUv * vec2(1.0, -1.0), lod);
    color += textureLod(tex, uv + dUv * vec2(-1.0, 1.0), lod);
    color += textureLod(tex, uv + dUv * vec2(-1.0, -1.0), lod);
    color *= 0.2;

    FragColor = color;
}
