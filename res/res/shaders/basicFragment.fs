#version 400
in vec2 texCoord0;

uniform sampler2D texture_sampler;
uniform vec3 lightColor;

in vec3 surfaceNormal;
in vec3 toLightVector;

out vec4 out_Color;
const float ambientLight = 0.75;
void main(){
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightNormal = normalize(toLightVector);

    float ndot1 = dot(unitNormal, unitLightNormal);
    float brightness = max(ndot1,ambientLight);
    vec3 diffuse = brightness*lightColor;

    out_Color = vec4(diffuse ,1.0)*    texture2D(texture_sampler, texCoord0);
}

