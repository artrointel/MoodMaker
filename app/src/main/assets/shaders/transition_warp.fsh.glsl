#version 320 es
precision highp float;

uniform sampler2D tex;

uniform float uAlpha;
uniform float uProgress;
in vec2 vUv;
out vec4 FragColor;

// https://www.shadertoy.com/view/lsdXDH
vec4 generic_desaturate(vec3 color, float factor)
{
    vec3 lum = vec3(0.299, 0.587, 0.114);
    vec3 gray = vec3(dot(lum, color));
    return vec4(mix(color, gray, factor), 1.0);
}

// https://gl-transitions.com/editor/directionalwarp
const vec2 direction = vec2(0.0, -1.0);
const float smoothness = 1.8;
const vec2 center = vec2(0.5, 0.5);

vec4 warp(vec2 uv) {
    vec2 v = normalize(direction);
    v /= abs(v.x) + abs(v.y);
    float d = v.x * center.x + v.y * center.y;
    float m = 1.0 - smoothstep(-smoothness, 0.0, v.x * uv.x + v.y * uv.y - (d - 0.5 + uProgress * (1.0 + smoothness)));
    vec4 grayscale = texture(tex, (uv - 0.5) * m + 0.5);
    grayscale = generic_desaturate(grayscale.xyz, 0.5);
    return mix(texture(tex, (uv - 0.5) * (1.0 - m) + 0.5), vec4(grayscale.xyz, 0.3), m);
}

void main() {
    vec4 color = warp(vUv);
    color.a = color.a * uAlpha;
    FragColor = color;
}
