package com.mao.sauces.block.blockentity;

import com.mao.sauces.recipe.SauceMakingMachineRecipe;
import com.mao.sauces.registry.EntityTypesRegistry;
import com.mao.sauces.registry.SoundsRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SauceMakingMachineBlockEntity extends BlockEntity implements BlockEntityInv {
    public final RecipeType<SauceMakingMachineRecipe> recipeType = SauceMakingMachineRecipe.Type.INSTANCE;
    private int processTimeTotal;
    protected int processTime;
    protected boolean isProcessing = false;
    private Direction itemDirection = Direction.NORTH;
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private final DefaultedList<ItemStack> bottle = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public SauceMakingMachineBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypesRegistry.SAUCE_MAKING_MACHINE_BLOCK_ENTITY, pos, state);
    }

    public Direction getItemDirection() {
        return itemDirection;
    }

    public boolean isProcessing() {
        return isProcessing;
    }

    public int getProcessTime() {
        return processTime;
    }

    public void setItemDirection(Direction itemDirection) {
        this.itemDirection = itemDirection;
    }

    public ItemStack getBottleItem() {
        return bottle.get(0);
    }

    public void setBottleItem(ItemStack stack) {
        bottle.set(0, stack);
    }

    public static void tick(World world, BlockPos pos, BlockState state, SauceMakingMachineBlockEntity entity) {
        ItemStack stack = entity.getItems().get(0);
        if (!stack.isEmpty()) {
            Optional<SauceMakingMachineRecipe> optional = world.getRecipeManager().getFirstMatch(entity.recipeType, entity, world);
            if (optional.isPresent()){
                SauceMakingMachineRecipe recipe = optional.get();
                entity.processTimeTotal = recipe.getProcesstime();
                entity.isProcessing = true;
                entity.processTime++;
                spawnItemParticles(world, pos, stack, 5);

                if (entity.processTime == entity.processTimeTotal) {
                    entity.processTime = 0;
                    entity.isProcessing = false;
                    ItemStack output = recipe.craft(entity, world.getRegistryManager());
                    ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), entity.getStack(0).getRecipeRemainder());
                    entity.setBottleItem(output);
                    world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundsRegistry.WORK_FINISHED, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
                    entity.setStack(0, ItemStack.EMPTY);
                }
            }
        }
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, bottle);
        Inventories.writeNbt(nbt, items);
        nbt.putInt("ProcessTimeTotal", this.processTimeTotal);
        nbt.putInt("ProcessTime", this.processTime);
        nbt.putString("ItemDirection", this.itemDirection.name());
        nbt.putBoolean("isProcessing", this.isProcessing);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, bottle);
        Inventories.readNbt(nbt, items);
        this.processTimeTotal = nbt.getInt("ProcessTimeTotal");
        this.processTime = nbt.getInt("ProcessTime");
        this.isProcessing = nbt.getBoolean("isProcessing");
        this.itemDirection = Direction.valueOf(nbt.getString("ItemDirection"));
    }

    public static void spawnItemParticles(World worldIn, BlockPos pos, ItemStack stack, int count) {
        for (int i = 0; i < count; ++i) {
            Vec3d vec3d = new Vec3d(((double) worldIn.getRandom().nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, ((double) worldIn.getRandom().nextFloat() - 0.5) * 0.1);
            if (worldIn instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(new ItemStackParticleEffect(ParticleTypes.ITEM, stack), (float) pos.getX() + 0.5F, (float) pos.getY() + 0.1F, (float) pos.getZ() + 0.5F, 1, vec3d.x, vec3d.y + 0.05, vec3d.z, 0.0);
            } else {
                worldIn.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, stack), (float) pos.getX() + 0.5F, (float) pos.getY() + 0.1F, (float) pos.getZ() + 0.5F, vec3d.x, vec3d.y + 0.05, vec3d.z);
            }
        }
    }
}
