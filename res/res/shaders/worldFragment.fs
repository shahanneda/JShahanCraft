#version 400

in vec3 color0;
in vec3 surfaceNormal;
in vec3 toLightVector;

uniform vec3 sun;

void main(){
    vec3 unityNormal = normalize(surfaceNormal);
    vec3 unitLightNormal = normalize(toLightVector);

    float ndot1 = dot(unityNormal, unitLightNormal);
    float brightness = max(ndot1,0.0);
    vec3 diffuse = brightness*color0;
    gl_FragColor = brightness * vec4(color0,1.0);
}