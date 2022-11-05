#version 320 es
precision highp float;

uniform sampler2D tex;
uniform sampler2D tex2;

uniform float uAlpha;
uniform float uProgress;
uniform vec2 uResolution;
in vec2 vUv;
out vec4 FragColor;

// https://www.shadertoy.com/view/lsdXDH
vec4 generic_desaturate(vec3 color, float factor)
{
    vec3 lum = vec3(0.299, 0.587, 0.114);
    vec3 gray = vec3(dot(lum, color));
    return vec4(mix(color, gray, factor), 1.0);
}

// https://www.shadertoy.com/view/3ljfzV
void main() {
    // Tweakable parameters
    float freq = 6.0;
    float period = 8.0;
    float speed = 7.9;
    float fade = 4.0;
    float displacement = 0.15;

    vec2 R = uResolution.xy,
    U = ((2. * vUv * uResolution) - R) / min(R.x, R.y),
    T = vUv;
    float D = length(U);

    float frame_time = mod(uProgress * speed, period);
    float pixel_time = max(0.0, frame_time - D);

    float wave_height = (cos(pixel_time * freq) + 1.0) / 2.0;
    float wave_scale = (1.0 - min(1.0, pixel_time / fade));
    float frac = wave_height * wave_scale;
    if (mod(uProgress * speed, period * 2.0) > period)
    {
        frac = 1. - frac;
    }

    vec2 tc = T + ((U / D) * -((sin(pixel_time * freq) / fade) * wave_scale) * displacement);

    vec4 color = texture(tex, tc);
    color.a = color.a * uAlpha;
    FragColor = mix(texture(tex2, tc), color, frac);
}
