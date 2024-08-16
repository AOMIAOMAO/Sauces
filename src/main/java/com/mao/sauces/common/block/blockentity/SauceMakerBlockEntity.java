package com.mao.sauces.common.block.blockentity;

import com.mao.sauces.common.block.SauceMakerBlock;
import com.mao.sauces.common.recipe.SauceMakerRecipe;
import com.mao.sauces.common.recipe.SauceMakerRecipeInv;
import com.mao.sauces.registry.ModBlockEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SauceMakerBlockEntity extends BlockEntity implements BlockEntityInv, SauceMakerRecipeInv {
    private final DefaultedList<ItemStack> inv = DefaultedList.ofSize(4, ItemStack.EMPTY);
    private int makingTime;
    private int makingTimeTotal;

    /* client only */
    private boolean isMaking = false;
    private int rotationTime = 0;

    public SauceMakerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.SAUCE_MAKER, pos, state);
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, SauceMakerBlockEntity entity) {
        entity.making();
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, SauceMakerBlockEntity entity){
        Optional<SauceMakerRecipe> optional = world.getRecipeManager().getFirstMatch(SauceMakerRecipe.Type.INSTANCE, entity, world);
        if (entity.getIngredients().size() == 3){
            if (optional.isPresent()) {
                SauceMakerRecipe recipe = optional.get();
                if (entity.canMake(recipe)) {
                    entity.isMaking = true;
                    entity.rotationTime++;
                }else {
                    entity.isMaking = false;
                    entity.rotationTime = 0;
                }
            }
        }
    }

    public void making(){
        if (world == null) return;
        if (getIngredients().size() == 3){
            Optional<SauceMakerRecipe> optional = world.getRecipeManager().getFirstMatch(SauceMakerRecipe.Type.INSTANCE, this, world);

            if (optional.isPresent()){
                SauceMakerRecipe recipe = optional.get();
                if (canMake(recipe)) {
                    makingTimeTotal = recipe.getMakingTime();
                    makingTime++;

                    if (makingTime == makingTimeTotal) {
                        setResult(recipe);
                    }
                }
            }
        }
    }

    private void setResult(SauceMakerRecipe recipe){
        if (world == null) return;
        ItemStack result = recipe.craft(this, world.getRegistryManager());
        clear();
        setStack(3, result);
        makingTime = makingTimeTotal = 0;
        world.setBlockState(pos, getCachedState().with(SauceMakerBlock.HAS_WATER, false));
        inventoryChanged();
    }

    public boolean canMake(@Nullable SauceMakerRecipe recipe){
        return recipe != null && recipe.matches(this, world) && world.getBlockState(pos).get(SauceMakerBlock.HAS_WATER);
    }
    /* client only */
    public boolean isMaking(){
        return isMaking;
    }

    public int getRotationTime() {
        return rotationTime;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inv;
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }

    @Override
    public @NotNull List<ItemStack> getIngredients() {
        List<ItemStack> ret = new ArrayList<>();
        if (isEmpty()) return ret;
        for (int i = 0; i < 3; i++) {
            if (!getStack(i).isEmpty()) ret.add(getStack(i));
        }
        return ret;
    }

    @Override
    public @NotNull ItemStack getContainer() {
        return getStack(3);
    }

    public boolean removeItems(PlayerEntity player){
        for (int i = size()-1; i >=0; i--) {
            ItemStack stack1 = getStack(i);
            if (!stack1.isEmpty()){
                player.getInventory().offerOrDrop(stack1.split(1));
                inventoryChanged();
                return true;
            }
        }
        return false;
    }

    public void inventoryChanged() {
        this.markDirty();
        if (world != null) {
            world.updateListeners(getPos(), getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inv);
        nbt.putBoolean("isMaking", isMaking);
        nbt.putInt("makingTime", makingTime);
        nbt.putInt("makingTimeTotal", makingTimeTotal);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inv.clear();
        Inventories.readNbt(nbt, this.inv);
        this.makingTime = nbt.getInt("makingTime");
        this.makingTimeTotal = nbt.getInt("makingTimeTotal");
        this.isMaking = nbt.getBoolean("isMaking");
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        Inventories.writeNbt(nbtCompound, this.inv, true);
        return nbtCompound;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}
