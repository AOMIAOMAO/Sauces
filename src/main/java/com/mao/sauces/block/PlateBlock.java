package com.mao.sauces.block;

import com.mao.sauces.block.blockentity.PlateBlockEntity;
import com.mao.sauces.item.SaucesItem;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PlateBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public PlateBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof PlateBlockEntity entity) {
            ItemStack food = entity.getStack(0);
            ItemStack stack = player.getStackInHand(hand);

            if (food.isEmpty()) {
                entity.setStack(0, stack.split(1));
                entity.setItemDirection(player.getHorizontalFacing());
            } else if (food.isFood()){
                if (stack.getItem() instanceof SaucesItem sauces && !food.hasNbt()) {
                    sauces.spreadSauces(food, player);
                    spawnItemParticles(world, pos, food, 7);
                    stack.damage(1, player, p -> p.sendToolBreakStatus(hand));
                }else {
                    player.giveItemStack(food.split(1));
                }
            }else {
                player.giveItemStack(food.split(1));
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof PlateBlockEntity) {
            if (world instanceof ServerWorld) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
            }
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 1.0, 15.0);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PlateBlockEntity(pos, state);
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
