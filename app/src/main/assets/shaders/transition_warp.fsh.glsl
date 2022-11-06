#version 320 es
precision highp float;

uniform sampler2D tex;
uniform sampler2D tex2;

uniform float uAlpha;
uniform float uProgress;
in vec2 vUv;
out vec4 FragColor;

// https://gl-transitions.com/editor/directionalwarp
const vec2 direction = vec2(0.0, -1.0);
const float smoothness = 1.8;
const vec2 center = vec2(0.5, 0.5);

vec4 warp(vec2 uv) {
    vec2 v = normalize(direction);
    v /= abs(v.x) + abs(v.y);
    float d = v.x * center.x + v.y * center.y;
    float m = 1.0 - smoothstep(-smoothness, 0.0, v.x * uv.x + v.y * uv.y - (d - 0.5 + uProgress * (1.0 + smoothness)));
    return mix(texture(tex, (uv - 0.5) * (1.0 - m) + 0.5),
                texture(tex2, (uv - 0.5) * m + 0.5), m);
}

void main() {
    vec4 color = warp(vUv);
    color.a = color.a * uAlpha;
    FragColor = color;
}
