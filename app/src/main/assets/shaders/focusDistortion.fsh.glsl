#version 320 es
precision highp float;

uniform sampler2D tex;

in vec2 vUv;
out vec4 FragColor;

void main() {
    vec4 color = 0.1f*texture(tex, vUv);
    color.xy += gl_FragCoord.xy;
    FragColor = color;
}
