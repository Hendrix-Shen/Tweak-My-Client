package top.hendrixshen.tweakmyclient.helper;

import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

public class AreaBox {
    private final int minX;
    private final int minY;
    private final int minZ;
    private final int maxX;
    private final int maxY;
    private final int maxZ;

    public AreaBox(int x1, int y1, int z1, int x2, int y2, int z2) {
        this.minX = Math.min(x1, x2);
        this.minY = Math.min(y1, y2);
        this.minZ = Math.min(z1, z2);
        this.maxX = Math.max(x1, x2);
        this.maxY = Math.max(y1, y2);
        this.maxZ = Math.max(z1, z2);
    }

    public AreaBox(@NotNull BlockPos blockPos1, @NotNull BlockPos blockPos2) {
        this(blockPos1.getX(), blockPos1.getY(), blockPos1.getZ(), blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
    }

    public int getMinX() {
        return this.minX;
    }

    public int getMinY() {
        return this.minY;
    }

    public int getMinZ() {
        return this.minZ;
    }

    public int getMaxX() {
        return this.maxX;
    }

    public int getMaxY() {
        return this.maxY;
    }

    public int getMaxZ() {
        return this.maxZ;
    }

    public boolean contains(int x, int y, int z) {
        return x >= this.minX && x <= this.maxX && y >= this.minY && y <= this.maxY && z >= this.minZ && z <= this.maxZ;
    }

    public boolean contains(@NotNull BlockPos blockPos) {
        return this.contains(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public boolean intersects(@NotNull AreaBox areaBox) {
        return this.maxX >= areaBox.minX
                && this.minX <= areaBox.maxX
                && this.maxZ >= areaBox.minZ
                && this.minZ <= areaBox.maxZ
                && this.maxY >= areaBox.minY
                && this.minY <= areaBox.maxY;
    }

    public int getXSize() {
        return this.maxX - this.minX;
    }

    public int getYSize() {
        return this.maxY - this.minY;
    }

    public int getZSize() {
        return this.maxZ - this.minZ;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + this.maxX;
        result = prime * result + this.maxY;
        result = prime * result + this.maxZ;
        result = prime * result + this.minX;
        result = prime * result + this.minY;
        return prime * result + this.minZ;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof AreaBox) {
            AreaBox areaBox = (AreaBox) obj;
            if (areaBox.minX != this.minX) {
                return false;
            } else if (areaBox.minY != this.minY) {
                return false;
            } else if (areaBox.minZ != this.minZ) {
                return false;
            } else if (areaBox.maxX != this.maxX) {
                return false;
            } else if (areaBox.maxY != this.maxY) {
                return false;
            } else {
                return areaBox.maxZ == this.maxZ;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "AreaBox[" + this.minX + ", " + this.minY + ", " + this.minZ + "] -> [" + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
    }
}
