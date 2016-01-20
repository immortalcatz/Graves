package com.fireball1725.graves.block;

import com.fireball1725.graves.tileentity.TileEntityGraveStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockGraveStone extends BlockBase {
	public static final PropertyDirection FACING = PropertyDirection.create("FACING", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool HASLID = PropertyBool.create("haslid");

    public BlockGraveStone() {
        super(Material.cloth);
		setDefaultState(blockState.getBaseState().withProperty(HASLID, true).withProperty(FACING, EnumFacing.NORTH));
		this.setHardness(0.5F);
		this.setResistance(10000.0F);
		this.setTileEntity(TileEntityGraveStone.class);
    }

    @Override
    public boolean canEntityDestroy(IBlockAccess world, BlockPos pos, Entity entity) {
		return false;
	}

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

	@Override
	public int getRenderType()
	{
		return 3;
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, HASLID, FACING);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if(tileEntity != null && tileEntity instanceof TileEntityGraveStone)
		{
			TileEntityGraveStone graveStone = (TileEntityGraveStone) tileEntity;
			return state.withProperty(HASLID, graveStone.hasLid());
		}
		return state.withProperty(HASLID, false);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(FACING, EnumFacing.values()[meta]);
	}

	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getIndex();
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
	{
		IBlockState actualState = getActualState(worldIn.getBlockState(pos), worldIn, pos);
		if(actualState.getValue(HASLID))
		{
			//			setBlockBounds(pos.getX(), pos.getY() - 1f, pos.getZ(), 2f, 1.25f, 1);
		}
	}

	@Override
	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity)
	{
		//		list.clear();
		IBlockState actualState = getActualState(worldIn.getBlockState(pos), worldIn, pos);
		if(actualState.getValue(HASLID))
		{
			//			list.add()
			//			setBlockBounds(pos.getX(), pos.getY() - 1f, pos.getZ(), pos.getX() + 2f, pos.getY() + .25f, pos.getZ() + 1);
		}
	}

	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}


}
