package wisniautilitypack.wisniautilitypack.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.*;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class Renderer {

    public static void drawBox(MatrixStack matrices, Camera cam, int x1, int y1,int z1, int x2, int y2, int z2, Colors.ColorRGBA color,boolean outline){

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        RenderSystem.setShaderColor(color.getR(),color.getG(),color.getB(), color.getA());
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        Box boxx = new Box(x1-cam.getPos().x,y1-cam.getPos().y,z1-cam.getPos().z,x2-cam.getPos().x,y2-cam.getPos().y,z2-cam.getPos().z);

        if(outline){
            bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP,
                    VertexFormats.POSITION);
        }else {
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS,
                    VertexFormats.POSITION);
        }

        // line
        bufferBuilder
                .vertex(matrix, (float) boxx.minX, (float) boxx.minY, (float) boxx.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) boxx.maxX, (float) boxx.minY, (float) boxx.minZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.minY, (float)boxx.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.minY, (float)boxx.maxZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.maxY, (float)boxx.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.maxY, (float)boxx.maxZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.maxY, (float)boxx.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.maxY, (float)boxx.minZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float) boxx.minX, (float) boxx.minY, (float) boxx.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) boxx.minX, (float) boxx.maxY, (float) boxx.minZ)
                .next();

        // line

        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.maxY, (float)boxx.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.minY, (float)boxx.minZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.minY, (float)boxx.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.maxY, (float)boxx.minZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.maxY, (float)boxx.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.minY, (float)boxx.maxZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.minY, (float)boxx.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.minY, (float)boxx.maxZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.maxY, (float)boxx.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.maxY, (float)boxx.maxZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.minY, (float)boxx.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.minY, (float)boxx.maxZ)
                .next();
        // line

        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.maxY, (float)boxx.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.maxY, (float)boxx.minZ)
                .next();

        tessellator.draw();

        RenderSystem.setShaderColor(1, 1, 1, 1);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }
    public static void drawBox(MatrixStack matrices, int x1, int y1,int z1, int x2, int y2, int z2, Colors.ColorRGBA color,boolean outline){

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        RenderSystem.setShaderColor(color.getR(),color.getG(),color.getB(), color.getA());
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        Box boxx = new Box(x1,y1,z1,x2,y2,z2);

        if(outline){
            bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP,
                    VertexFormats.POSITION);
        }else {
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS,
                    VertexFormats.POSITION);
        }

        // line
        bufferBuilder
                .vertex(matrix, (float) boxx.minX, (float) boxx.minY, (float) boxx.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) boxx.maxX, (float) boxx.minY, (float) boxx.minZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.minY, (float)boxx.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.minY, (float)boxx.maxZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.maxY, (float)boxx.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.maxY, (float)boxx.maxZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.maxY, (float)boxx.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.maxY, (float)boxx.minZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float) boxx.minX, (float) boxx.minY, (float) boxx.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) boxx.minX, (float) boxx.maxY, (float) boxx.minZ)
                .next();

        // line

        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.maxY, (float)boxx.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.minY, (float)boxx.minZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.minY, (float)boxx.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.maxY, (float)boxx.minZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.maxY, (float)boxx.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.minY, (float)boxx.maxZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.minY, (float)boxx.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.minY, (float)boxx.maxZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.maxY, (float)boxx.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.maxY, (float)boxx.maxZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.minY, (float)boxx.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.minY, (float)boxx.maxZ)
                .next();
        // line

        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.maxY, (float)boxx.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.maxY, (float)boxx.minZ)
                .next();

        tessellator.draw();

        RenderSystem.setShaderColor(1, 1, 1, 1);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }
    public static void drawBox(MatrixStack matrices, Camera cam, Box box, Colors.ColorRGBA color,boolean outline){

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        RenderSystem.setShaderColor(color.getR(),color.getG(),color.getB(), color.getA());
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        Box boxx = new Box(box.minX-cam.getPos().x,box.minY-cam.getPos().y,box.minZ-cam.getPos().z,box.maxX-cam.getPos().x,box.maxY-cam.getPos().y,box.maxZ-cam.getPos().z);

        if(outline){
            bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP,
                    VertexFormats.POSITION);
        }else {
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS,
                    VertexFormats.POSITION);
        }

        // line
        bufferBuilder
                .vertex(matrix, (float) boxx.minX, (float) boxx.minY, (float) boxx.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) boxx.maxX, (float) boxx.minY, (float) boxx.minZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.minY, (float)boxx.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.minY, (float)boxx.maxZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.maxY, (float)boxx.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.maxY, (float)boxx.maxZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.maxY, (float)boxx.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.maxY, (float)boxx.minZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float) boxx.minX, (float) boxx.minY, (float) boxx.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) boxx.minX, (float) boxx.maxY, (float) boxx.minZ)
                .next();

        // line

        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.maxY, (float)boxx.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.minY, (float)boxx.minZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.minY, (float)boxx.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.maxY, (float)boxx.minZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.maxY, (float)boxx.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.minY, (float)boxx.maxZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.minY, (float)boxx.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.minY, (float)boxx.maxZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.maxX, (float)boxx.maxY, (float)boxx.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.maxY, (float)boxx.maxZ)
                .next();

        // line
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.minY, (float)boxx.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.minY, (float)boxx.maxZ)
                .next();
        // line

        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.maxY, (float)boxx.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)boxx.minX, (float)boxx.maxY, (float)boxx.minZ)
                .next();

        tessellator.draw();

        RenderSystem.setShaderColor(1, 1, 1, 1);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static void drawLineCamOffset(MatrixStack matrices, float x1, float y1, float z1, float x2, float y2 , float z2, Colors.ColorRGBA color) {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        Vec3d camPos = MinecraftClient.getInstance().cameraEntity.getPos();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP,
                VertexFormats.POSITION);

        RenderSystem.setShaderColor(color.getR(),color.getG(),color.getB(), color.getA());

        bufferBuilder.vertex(matrix, (float)(x1 - camPos.x),(float)(y1 - camPos.y), (float)(z1 - camPos.z)).next();
        bufferBuilder.vertex(matrix, (float)(x2 - camPos.x),(float)(y2 - camPos.y), (float)(z2 - camPos.z)).next();

        tessellator.draw();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static void drawLine(MatrixStack matrices, float x1, float y1, float z1, float x2, float y2 , float z2, Colors.ColorRGBA color) {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP,
                VertexFormats.POSITION);

        RenderSystem.setShaderColor(color.getR(),color.getG(),color.getB(), color.getA());

        bufferBuilder.vertex(matrix, x1,y1, z1).next();
        bufferBuilder.vertex(matrix, x2,y2, z2).next();

        tessellator.draw();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }
    public static void drawLine2(MatrixStack matrices,Camera cam, float x1, float y1, float z1, float x2, float y2 , float z2, Colors.ColorRGBA color) {
        /*GL11.glDisable(GL11.GL_DEPTH_TEST);
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        Vec3d camPos = cam.getPos();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP,
                VertexFormats.POSITION);

        RenderSystem.setShaderColor(color.getR(),color.getG(),color.getB(), color.getA());


        bufferBuilder.vertex(matrix, (float)(x2 - camPos.x),(float)(y2 - camPos.y), (float)(z2 - camPos.z)).next();

        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        MatrixStack matrixStack = RenderSystem.getModelViewStack();
        matrixStack.push();
        matrixStack.translate(MinecraftClient.getInstance().getWindow().getScaledWidth() / 2, MinecraftClient.getInstance().getWindow().getScaledHeight() / 2, 0);
        matrixStack.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(camera.getPitch()));
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(camera.getYaw()));
        matrixStack.scale(-1.0f, -1.0f, -1.0f);
        RenderSystem.applyModelViewMatrix();
        bufferBuilder.vertex(matrix, (float)(x1 - camPos.x),(float)(y1 - camPos.y), (float)(z1 - camPos.z)).next();
        matrixStack.pop();
        RenderSystem.applyModelViewMatrix();

        tessellator.draw();
        GL11.glEnable(GL11.GL_DEPTH_TEST);*/
    }
    public static void drawText(MatrixStack matrices,Camera cam, float x1, float y1, float z1, Colors.ColorRGBA color) {


    }

}
