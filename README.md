# JShahanCraft

# A 3D Gameengine made from stratch in OpenGl.
### **Note: I did not use version control when i developed this project, just uploading it to git so it does not get lost**
#### **No extra libarys and tools used other than perlin noise, and Opengl(using lwjgl wrapper)**
Features inculde: 
* 3D rendering of entities
* World split into diffrent optimised chunks
* Infinite Chunk generation in all directions
* Optimised 3D rendering of terrian voxels and chunks **USING MULTIPLE THREADS**
* Simple and efficient lighting using simple normal calculations for each triangle
* World generation using perlin noise
* Culling to get rid of extra triangles and optimise rendering
* Texture atlas and only **ONE** shader and texture loaded for all objects
