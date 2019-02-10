# JShahanCraft

# A 3D Gameengine made from stratch in OpenGl.
### **No extra libraries and tools used other than perlin noise, and Opengl(using lwjgl wrapper)**
###[Download](https://github.com/shahanneda/JShahanCraft/releases/download/v0.1/JShahanCraft.jar)

#### **Note: I did not use version control when i developed this project, just uploading it to git so it does not get lost**
Features inculde: 

* World split into diffrent optimised chunks
* Infinite Chunk generation in all directions
* Optimised 3D rendering of terrian voxels and chunks **USING MULTIPLE THREADS**
* Simple and efficient lighting using simple normal calculations for each triangle
* World generation using perlin noise
* Culling to get rid of extra triangles and optimise rendering
* Texture atlas and only **ONE** shader and texture loaded for all objects
* 3D rendering of entities
* Different Biomes including Sandy, Water, Grassland, stone, and the ablity to add as many as needed 
* Biomes also are generated with perlin noise

## All Images here are directly from the game, the game is using the same textures as minecraft, but it can be run with any texxture simply by changing the /res/res/textures/grassBlock.png , this a texture atlas with all the textures

![image](ShahanCraftImages/1.png | width=300)

![image](ShahanCraftImages/5.png | width=300)
