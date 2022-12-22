#version 320 es
precision highp float;

uniform sampler2D tex;

#define PI 3.14159265359
#define CAMERA_DIST 1.25
#define LOD_BLUR 7.0

uniform float uAlpha;
uniform int uFadeDirX;
uniform int uFadeDirY;
uniform float uProgress;
uniform vec2 uResolution;
in vec2 vUv;
out vec4 FragColor;

//
void main() {
    vec2 p = (2. * vUv.xy * uResolution.xy - uResolution.xy) / uResolution.xy;
    p *= 0.5;
    float displayRatio = uResolution.x / uResolution.y;
    p.x *= displayRatio;

    float t = mod(2.*(1. - uProgress), 2.1);

    if (t < 1.) {
        float zoom = pow(2. * t, 5.) + 1.;
        vec3 dir = normalize(vec3(PI * p, -zoom * (CAMERA_DIST - 1.)));
        float b = CAMERA_DIST * dir.z;
        float h = b * b - CAMERA_DIST * CAMERA_DIST + 1.;
        if (h >= 0.) {
            vec3 q = vec3(0., 0.0, CAMERA_DIST) - dir * (b + sqrt(h));
            p = zoom * vec2(atan(q.x, q.z) / PI, 0.5 - acos(q.y / length(q)) / PI);
        } else {
            FragColor = vec4(0.0, 0.0, 0.0, 1.0);
            return;
        }
    }
    p.x /= displayRatio;
    vec2 uv = p * clamp(t, 0.5, 1.0) + 0.5;
    FragColor = texture(tex, uv);

    // do simple blur
    /*
    float normRadius = uProgress;
    float lod = normRadius * normRadius * 6.0 + 1.0; // map to [1,8] with sqrt interpolation
    float dUv = lod - 1.0;
    dUv *= 0.001;

    vec4 color = textureLod(tex, uv, lod);
    color += textureLod(tex, uv + dUv * vec2(1.0, 1.0), lod);
    color += textureLod(tex, uv + dUv * vec2(1.0, -1.0), lod);
    color += textureLod(tex, uv + dUv * vec2(-1.0, 1.0), lod);
    color += textureLod(tex, uv + dUv * vec2(-1.0, -1.0), lod);
    color *= 0.2;
    FragColor = color;
    */
}
