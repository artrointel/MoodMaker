#version 320 es
precision highp float;

uniform sampler2D tex;

uniform float uAlpha;
uniform int uFadeDirX;
uniform int uFadeDirY;
uniform float uProgress;
uniform vec2 uResolution;
in vec2 vUv;
out vec4 FragColor;

const float corner_harshness = 0.5;
const float corner_ease = 4.;
const float fade_amount = 16.;
const float fade_speed = 2.0;
const float half_pi = 3.141592 * 0.5;

// https://www.shadertoy.com/view/DllGDr#
void main() {
    // Normalized pixel coordinates (from 0 to 1)
    vec2 uv = vUv;

    float interpolation = pow(sin(uProgress * half_pi) * fade_speed, fade_amount); // todo
    float fade = max(interpolation, 1.);

    float xx=(abs(uv.x - 0.5) * corner_harshness) * (uFadeDirX == 1 ? fade : 1.0);
    float yy=(abs(uv.y - 0.5) * corner_harshness) * (uFadeDirY == 1 ? fade : 1.0);
    float rr=(1. + pow((xx * xx + yy * yy), corner_ease));

    vec2 tuv = (uv - 0.5) * rr + 0.5;
    tuv = clamp(tuv, 0., 1.);

    vec4 color = texture(tex, tuv);

    const float maximalInterpolation = pow(fade_speed, fade_amount*0.5);
    float color_distortion = sqrt(interpolation / maximalInterpolation); // [0,1]
    if(color_distortion > 0.)
    FragColor = color + (vec4(1.0)-color) * color_distortion;
    else
    FragColor = color;

    if(interpolation >= pow(fade_speed * 0.95, fade_amount)) FragColor = vec4(0.0);

    if(tuv.x >= 1.0) FragColor = vec4(0.0);
    if(tuv.y >= 1.0) FragColor = vec4(0.0);
    if(tuv.x <= 0.0) FragColor = vec4(0.0);
    if(tuv.y <= 0.0) FragColor = vec4(0.0);
}
