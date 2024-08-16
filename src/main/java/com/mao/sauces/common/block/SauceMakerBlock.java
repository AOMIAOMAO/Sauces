package com.mao.sauces.common.block;

import com.mao.sauces.common.block.blockentity.SauceMakerBlockEntity;
import com.mao.sauces.registry.ModBlockEntityTypes;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
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
    private static final VoxelShape SHAPE;
    public static final BooleanProperty HAS_WATER = BooleanProperty.of("has_water");

    public SauceMakerBlock() {
        super(Settings.copy(Blocks.STONE));
        BlockState blockState = this.stateManager.getDefaultState().with(HorizontalFacingBlock.FACING, Direction.NORTH).with(HAS_WATER, false);
        this.setDefaultState(blockState);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HorizontalFacingBlock.FACING, HAS_WATER);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof SauceMakerBlockEntity) {
            if (world instanceof ServerWorld) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
            }
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(HorizontalFacingBlock.FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(HorizontalFacingBlock.FACING, rotation.rotate(state.get(HorizontalFacingBlock.FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(HorizontalFacingBlock.FACING)));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);

        if (world.getBlockEntity(pos) instanceof SauceMakerBlockEntity maker){
            if (this.fillWater(world, pos, stack, player)) {
                return ActionResult.success(world.isClient());
            }

            if (!stack.isEmpty()){
                for (int i = 0; i < maker.size(); ++i) {
                    ItemStack itemStack = maker.getStack(i);
                    if (itemStack.isEmpty()){
                        maker.setStack(i, stack.split(1));
                        return ActionResult.success(world.isClient());
                    }
                    maker.inventoryChanged();
                }
            }else if (maker.removeItems(player)){
                return ActionResult.success(world.isClient());
            }
        }
        return ActionResult.PASS;
    }

    private boolean fillWater(World world, BlockPos pos, ItemStack stack, PlayerEntity player){
        BlockState state = world.getBlockState(pos);
        if (PotionUtil.getPotion(stack) == Potions.WATER && !state.get(HAS_WATER)){
            world.setBlockState(pos, state.with(HAS_WATER, true));
            player.setStackInHand(player.getActiveHand(), ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
            return true;
        }
        return false;
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
        return !world.isClient ? checkType(type, ModBlockEntityTypes.SAUCE_MAKER, SauceMakerBlockEntity::serverTick) : checkType(type, ModBlockEntityTypes.SAUCE_MAKER, SauceMakerBlockEntity::clientTick) ;
    }

    static {
        VoxelShape voxelShape = Block.createCuboidShape(2, 0, 2, 14, 2, 14);
        VoxelShape voxelShape1 = Block.createCuboidShape(3, 2, 3, 13, 4, 13);
        VoxelShape voxelShape2 = Block.createCuboidShape(5, 4, 5, 11, 15, 11);
        SHAPE = VoxelShapes.union(voxelShape, voxelShape1, voxelShape2);
    }
}
