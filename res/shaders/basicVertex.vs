#version 400

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;
layout (location = 2) in vec3 normal;



out vec2 texCoord0;
out vec3 surfaceNormal;
out vec3 toLightVector;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 worldMatrix;
uniform vec3 lightPos;

void main(){
    vec4 worldPosition = worldMatrix * vec4(position, 1.0);
  gl_Position = projectionMatrix * viewMatrix* worldMatrix * vec4(position, 1.0);

    texCoord0 = texCoord;
    vec3 random = normal;
    surfaceNormal =(worldPosition*vec4(random,1.0)).xyz;

    toLightVector = vec3(worldPosition.x,worldPosition.y+10,worldPosition.z) - worldPosition.xyz;
}

