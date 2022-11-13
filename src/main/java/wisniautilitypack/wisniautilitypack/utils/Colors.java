package wisniautilitypack.wisniautilitypack.utils;

public class Colors {
    public record ColorRGBA(float r, float g, float b, float a){
        public float getR() {
            return r;
        }
        public float getG() {
            return g;
        }
        public float getB() {
            return b;
        }
        public float getA() {
            return a;
        }
    }
    public record ColorRGB(float r, float g, float b){
        public float getR() {
            return r;
        }
        public float getG() {
            return g;
        }
        public float getB() {
            return b;
        }
    }
    public ColorRGB RGBAtoRGB(ColorRGBA col){
        return new ColorRGB(col.r,col.g,col.b);
    }

    public static ColorRGBA RGBtoRGBA(ColorRGB col){
        return new ColorRGBA(col.r,col.g,col.b,1.0f);
    }
    public static ColorRGBA RGBtoRGBA(ColorRGB col, float alpha){
        return new ColorRGBA(col.r,col.g,col.b,alpha);
    }
    public static int RGBtoInt(ColorRGB color){
        int hexR = (int) color.r * 0xFF0000;
        int hexG = (int) color.g * 0x00FF00;
        int hexB = (int) color.b * 0x0000FF;

        return hexR + hexG + hexB;
    }
    public static ColorRGB intToRGB(int color){
        float floatR = (float) color * 0xFF0000;
        float floatG = (float) color * 0x00FF00;
        float floatB = (float) color * 0x0000FF;

        return new ColorRGB(floatR, floatG, floatB);
    }
}
