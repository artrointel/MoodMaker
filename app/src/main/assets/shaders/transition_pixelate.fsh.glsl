#version 320 es
precision highp float;

uniform sampler2D tex;

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

// https://www.shadertoy.com/view/MtdXDM
void main() {

    // Configurable
    float maximumBlockSize = 8.0;			// Maximum Block Size (2 ^ x)
    vec2 blockOffset = vec2(0.5, 0.5);		// Inner Block offset
    vec2 pixelateCenter = vec2(0.5, 0.5);	// Pixelate offset.

    // Animation Calculations
    float animProgress = uProgress;								// Time as Progress (0..1)
    float animStuff = 1.0 - (abs(animProgress - 0.5) * 2.0);					// Progress as a bounce value (0..1..0)
    // There are two ways to calculate this, one is pixel aligned the other is block aligned.
    float animBlockSize = floor(pow(2.0, maximumBlockSize * sqrt(sqrt(sqrt(animStuff)))));		// Block Size, always a multiple of 2. (Pixel Aligned)
    //float animBlockSize = pow(2.0, floor(maximumBlockSize * animStuff));		// Block Size, always a multiple of 2. (Block Aligned)
    // Block aligned block size needs a higher maximumBlockSize in order to look fluid, try changing it from 9 to 12 with it active.

    // UV Calculations
    vec2 finalUV = vUv;				// Use 0..1 UVs
    finalUV -= pixelateCenter;		// Offset by the pixelation center.
    finalUV *= uResolution.xy;		// Convert to 0..Resolution UVs for pixelation.
    finalUV /= animBlockSize;		// Divide by current block size.
    finalUV = floor(finalUV) + blockOffset;	// Use floor() on it to get aligned pixels. *1
    finalUV *= animBlockSize;		// Multiply by current block size.
    finalUV /= uResolution.xy;		// Convert back to 0..1 UVs for texture sampling.
    finalUV += pixelateCenter;		// Revert the offset by the pixelation center again.
    // Now we have fully pixelated UVs without needing any additional magic.
    // In theory it should be possible to do this without needing the texture resolution,
    //  but that one is much more than I want to do - and also really hard.
    //
    // *1: GLSL and HLSL can use round here to get a block that is centered on the pixelateCenter.

    // Using mix here to hide the obvious instant transition using a fade.
    // Can also be changed to "- 0.5) * 65535.0" for the instant transition effect.

    vec4 color = texture(tex, finalUV);
    color.a = color.a * uAlpha;
    FragColor = mix(color, generic_desaturate(color.xyz, 1.0), clamp((animProgress - 0.45) * 16.0, 0.0, 1.0));
}
