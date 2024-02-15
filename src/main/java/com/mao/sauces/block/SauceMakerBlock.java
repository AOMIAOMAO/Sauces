package com.mao.sauces.block;

import com.mao.sauces.block.blockentity.SauceMakerBlockEntity;
import com.mao.sauces.registry.EntityTypesRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SauceMakerBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public SauceMakerBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof SauceMakerBlockEntity entity) {
            ItemStack itemStack = entity.getStack(0);
            ItemStack itemStack1 = entity.getBottleItem();
            ItemStack stack = player.getStackInHand(hand);

            if (!entity.isProcessing()) {
                if (!hit.getSide().equals(Direction.UP)) {
                    if (itemStack.isEmpty()) {
                        entity.setStack(0, stack.split(stack.getCount()));
                        entity.setItemDirection(player.getHorizontalFacing());
                    } else if (!player.getInventory().insertStack(itemStack.split(itemStack.getCount()))) {
                        player.dropItem(itemStack.split(itemStack.getCount()), false);
                    }
                } else {
                    if (itemStack1.isEmpty()) {
                        entity.setBottleItem(stack.split(1));
                        entity.setItemDirection(player.getHorizontalFacing());
                    } else if (!player.getInventory().insertStack(itemStack1.split(1))) {
                        player.dropItem(itemStack1.split(1), false);
                    }
                }
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction dir = state.get(FACING);
        switch (dir) {
            case WEST -> {
                VoxelShape voxelShape = Block.createCuboidShape(3, 0, 1, 15, 2, 15);
                VoxelShape voxelShape1 = Block.createCuboidShape(4, 2, 3, 14, 4, 13);
                VoxelShape voxelShape2 = Block.createCuboidShape(6, 4, 5, 12, 16, 11);
                return VoxelShapes.union(voxelShape, voxelShape1, voxelShape2);
            }
            case EAST -> {
                VoxelShape voxelShape = Block.createCuboidShape(1, 0, 1, 13, 2, 15);
                VoxelShape voxelShape1 = Block.createCuboidShape(2, 2, 3, 12, 4, 13);
                VoxelShape voxelShape2 = Block.createCuboidShape(4, 4, 5, 10, 16, 11);
                return VoxelShapes.union(voxelShape, voxelShape1, voxelShape2);
            }
            case SOUTH -> {
                VoxelShape voxelShape = Block.createCuboidShape(1, 0, 1, 15, 2, 13);
                VoxelShape voxelShape1 = Block.createCuboidShape(3, 2, 2, 13, 4, 12);
                VoxelShape voxelShape2 = Block.createCuboidShape(5, 4, 4, 11, 16, 10);
                return VoxelShapes.union(voxelShape, voxelShape1, voxelShape2);
            }
            case NORTH -> {
                VoxelShape voxelShape = Block.createCuboidShape(1, 0, 3, 15, 2, 15);
                VoxelShape voxelShape1 = Block.createCuboidShape(3, 2, 4, 13, 4, 14);
                VoxelShape voxelShape2 = Block.createCuboidShape(5, 4, 6, 11, 16, 12);
                return VoxelShapes.union(voxelShape, voxelShape1, voxelShape2);
            }
            default -> {
                return VoxelShapes.fullCube();
            }
        }
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
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
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof SauceMakerBlockEntity entity) {
            if (world instanceof ServerWorld) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), entity.getBottleItem());
            }
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SauceMakerBlockEntity(pos, state);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, EntityTypesRegistry.SAUCE_MAKER_BLOCK_ENTITY, SauceMakerBlockEntity::tick);
    }
}
