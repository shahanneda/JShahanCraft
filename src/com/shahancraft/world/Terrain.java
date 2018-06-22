package com.shahancraft.world;

/**
 * Created by shahan on 11/25/2017.
 */
public class Terrain {

    public static int getBlockHeight(float x,float z,float cy){
        float perlinScale = 0.2f;
        float C1Freq = 0.60f;
        float C2Freq = 1;
        float C3Freq = 0.75f;
        float power = 1.9f;
        float heightModifer = 10;
        float anoisespread = 4f;
        OpenSimplexNoise noiseGen = new OpenSimplexNoise(123);
        double nx = x/150;
        double ny = z/150;
        double e = noiseGen.eval(nx*perlinScale*1,ny*perlinScale*1)
                + C1Freq * noiseGen.eval(nx*perlinScale*1, ny*perlinScale*1)
                + C2Freq * noiseGen.eval(nx*perlinScale*8, ny*perlinScale*8)
                + C3Freq * noiseGen.eval(nx*perlinScale*16, ny*perlinScale*16);
        double anoise =Math.pow(e, power);
        //double anoise = Math.round(e * 29 / 29);

        int height= (int)(Math.floor(anoise*anoisespread));
        height += heightModifer;
        //System.out.println(anoise);
        if (height<=0){
            height = 1;
        }
        if (cy ==0){
            if (height >=16){
                return 16;
            }
            if (height < 16){
                return height;
            }
        }
        if (cy==16*2){
            if (height <16){
                return 0;
            }
            if (height-16 >=16){
                return 16;
            }
            if (height >= 16){
                return  height-16;
            }

        }
        if (cy==32*2){
            if (height<=32){
                return 0;
            }
            if (height-16 >=16){
                return 16;
            }

            if (height >= 32){
                return height-32;
            }
        }
        if (cy==48*2){

            if (height<=48){
                return 0;
            }
            if (height-16 >=16){
                return 16;
            }
            if (height >= 64){
                return height-64;
            }
        }


        return 0;
    };

    public static Biome getBiome(float x,float z,float cy) {
        float perlinScale = 0.2f;
        float C1Freq = 0.40f;
        float C2Freq = 1;
        float C3Freq = 0.75f;
        float power = 1.7f;
        float heightModifer = 20;
        float anoisespread = 4f;
        OpenSimplexNoise moistureGen = new OpenSimplexNoise(123);
        double nx = x / 150;
        double ny = z / 150;
        double e = moistureGen.eval(nx * perlinScale * 1, ny * perlinScale * 1)
                + C1Freq * moistureGen.eval(nx * perlinScale * 1, ny * perlinScale * 1)
                + C2Freq * moistureGen.eval(nx * perlinScale * 8, ny * perlinScale * 8)
                + C3Freq * moistureGen.eval(nx * perlinScale * 16, ny * perlinScale * 16);
        double moiseture = Math.pow(e, power);

        if (moiseture<0.2){
            return Biome.Desert;
        }else if (moiseture > 0.9){
            return Biome.Lake;
        }
        else{
            return Biome.Planes;
        }
    }

}
