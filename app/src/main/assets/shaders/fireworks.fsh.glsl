#version 320 es
// Inspired from https://www.shadertoy.com/view/wtGfzy
precision highp float;
#define PI 3.1415926

uniform float uTime;
uniform float uSpeedFactor;

uniform float uMinCount;
uniform float uMaxCount;

uniform float uMinParticles;
uniform float uMaxParticles;

uniform float uMinBrightness;
uniform float uMaxBrightness;

in vec2 vUv;

out vec4 FragColor;

// All components are in the range [0…1], including hue.
vec3 HSV2RGB(vec3 c)
{
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

float Curve_Fadeout(float t)
{
    return (t -1.) * (t - 1.);
}

float BrightnessCurve_bk(float t)
{
    return (t*t*t*t - 2.*t*t*t + t*t) / 0.0625;
}

float BrightnessCurve(float t)
{
    return (t*t*t*t - 2.*t*t*t + t*t) / 0.0625;
}

float Rand11(float t)
{
    float x = fract(sin(413.3 * t + 1132.6));
    float rand = x * 2.0 -1.0;
    return rand;
}

vec2 Rand12(float t)
{
    float x = fract(sin(223.3 * t + 1132.6));
    float y = fract(cos(3322.2 * t - 122.1));
    vec2 rand2 = vec2(x, y) * 2.0 -1.0;
    return rand2;
}

vec2 Rand12_Round_Increase(float t)
{
    float a = 2.0 * PI * fract(sin(23.3 * t + 11.23));
    float r = (1.0 - Curve_Fadeout(fract(cos(322.2 * t + 111.2))));
    return vec2(r*cos(a), r*sin(a));
}

vec4 Rand14(float t)
{
    float x = fract(sin(123.3 * t + 654.6));
    float y = fract(cos(89.2 * t - 64.1));
    float z = fract(sin(203.4 * t - 11.1));
    float w = fract(cos(168.14 * t - 44.2));
    return vec4(x, y, z, w);
}

vec3 RandColor(float t, float brightRange)
{
    float h = 0.5 + 0.5*sin(0.5 * t);
    float s = 0.5 + 0.5*sin(t);
    float v = 1.0 - brightRange * (0.5 + 0.5 * sin(1.5 *t));
    return HSV2RGB(vec3(h,s,v));
}

float Firework(vec2 uv, float t, float pc, float pb)
{
    float brightness = pb;
    float dist = brightness / length(uv);
    float fireworkPattern = 0.0;
    for (float i = 0.0; i < 1.0; i += 1.0 / pc)
    {
        float particle = brightness / length(Rand12_Round_Increase(i + 1.0) * t + uv);
        float twinkle = cos(22.5 * (t + i)) * 0.5 + 0.5;
        fireworkPattern += particle * twinkle;
    }
    return fireworkPattern * BrightnessCurve(t);
}

void main()
{
    // Normalized pixel coordinates with screen ratio
    float time = uTime * uSpeedFactor;
    float timeline = fract(time);
    float periodID = floor(time);
    vec3 col = vec3(0.0);

    // Explore firework for each shoot
    float shoots = floor(mix(uMinCount, uMaxCount, 0.5 * Rand11(periodID) + 0.5));
    for (float s = 0.0; s < timeline;  s += 1. / shoots)
    {
        float t  = timeline - s;
        float tfl = floor(t) + periodID;
        float seed = tfl + s;
        vec4 rand = Rand14(seed);
        vec2 uvOffset = rand.xy - 0.5;
        //uvOffset.x *= screenRatio;
        float fireworkScale = mix(0.2, 0.6, rand.z);
        float particleCounts = mix(uMinParticles , uMaxParticles, rand.w);
        float particleBrightness = mix(uMaxBrightness, uMinBrightness, Curve_Fadeout((particleCounts - uMinParticles)/(uMaxParticles - uMinParticles)));
        float fireworkPattern = Firework((vUv + uvOffset) / fireworkScale, t, particleCounts, particleBrightness);
        vec3 fireworkColor = RandColor((rand.z + rand.w) * 10.0 , 0.2);
        col += fireworkPattern * fireworkColor;
    }

    // prev shoots
    periodID -= 1.0;
    float shoots_prev = floor(mix(uMinCount, uMaxCount, 0.5 * Rand11(periodID) + 0.5));
    for (float s = 1.0 - 1./shoots_prev; s > timeline; s -= 1. / shoots_prev)
    {
        float t  = timeline  + 1.0 - s;
        float tfl = floor(t) + periodID;
        float seed = tfl + s;
        vec4 rand = Rand14(seed);
        vec2 uvOffset = rand.xy - 0.5;
        //uvOffset.x *= screenRatio;
        float fireworkScale = mix(0.2, 0.6, rand.z);
        float particleCounts = mix(uMinParticles , uMaxParticles, rand.w);
        float particleBrightness = mix(uMaxBrightness, uMinBrightness, Curve_Fadeout((particleCounts - uMinParticles)/(uMaxParticles - uMinParticles)));
        float fireworkPattern = Firework((vUv + uvOffset) / fireworkScale, t, particleCounts, particleBrightness);
        vec3 fireworkColor = RandColor((rand.z + rand.w) * 10.0 , 0.2);
        col += fireworkPattern * fireworkColor;
    }

    FragColor = vec4(col, 1.0);
}