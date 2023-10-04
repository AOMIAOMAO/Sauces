package com.mao.sauces.block.blockentity;

import com.mao.sauces.registry.EntityTypesRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class PlateBlockEntity extends BlockEntity implements BlockEntityInv {
    private Direction itemDirection = Direction.NORTH;
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public PlateBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypesRegistry.PLATE_BLOCK_ENTITY, pos, state);
    }

    public Direction getItemDirection() {
        return itemDirection;
    }

    public void setItemDirection(Direction itemDirection) {
        this.itemDirection = itemDirection;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, items);
        nbt.putString("ItemDirection", this.itemDirection.name());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, items);
        this.itemDirection = Direction.valueOf(nbt.getString("ItemDirection"));
    }
}
