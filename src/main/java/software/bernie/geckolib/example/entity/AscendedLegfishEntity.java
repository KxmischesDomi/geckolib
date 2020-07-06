/*
 * Copyright (c) 2020.
 * Author: Bernie G. (Gecko)
 */

package software.bernie.geckolib.example.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import software.bernie.geckolib.entity.IAnimatedEntity;
import software.bernie.geckolib.animation.AnimationBuilder;
import software.bernie.geckolib.animation.model.AnimationController;
import software.bernie.geckolib.animation.model.AnimationControllerCollection;
import software.bernie.geckolib.animation.AnimationTestEvent;
import software.bernie.geckolib.example.KeyboardHandler;

public class AscendedLegfishEntity extends EntityMob implements IAnimatedEntity
{
	private static final DataParameter<Integer> SIZE = EntityDataManager.createKey(AscendedLegfishEntity.class, DataSerializers.VARINT);

	public AnimationControllerCollection animationControllers = new AnimationControllerCollection();

	private AnimationController sizeController = new AnimationController(this, "sizeController", 1F, this::sizeAnimationPredicate);
	private AnimationController moveController = new AnimationController(this, "moveController", 10F, this::moveController);

	public AscendedLegfishEntity(World worldIn)
	{
		super(worldIn);
		registerAnimationControllers();
	}

	private <ENTITY extends Entity> boolean moveController(AnimationTestEvent<ENTITY> entityAnimationTestEvent)
	{
		float limbSwingAmount = entityAnimationTestEvent.getLimbSwingAmount();
		if(KeyboardHandler.isForwardKeyDown)
		{
			moveController.setAnimation(new AnimationBuilder().addAnimation("kick", true));
			return true;
		}
		else if(KeyboardHandler.isBackKeyDown)
		{
			moveController.setAnimation(new AnimationBuilder().addAnimation("punchwalk", true));
			return true;
		}
		else if(!(limbSwingAmount > -0.15F && limbSwingAmount < 0.15F))
		{
			moveController.setAnimation(new AnimationBuilder().addAnimation("walk", true));
			return true;
		}
		return false;
	}


	private boolean hasGrown = false;
	private <ENTITY extends Entity> boolean sizeAnimationPredicate(AnimationTestEvent<ENTITY> entityAnimationTestEvent)
	{
		int size = getSize();
		switch(size)
		{
			case 1:
				sizeController.setAnimation(new AnimationBuilder().addAnimation("small"));
				break;
			case 2 :
				if(!hasGrown)
				{
 					sizeController.setAnimation(new AnimationBuilder().addAnimation("grow", false).addAnimation("upbig", true));
					setSize(3);
					hasGrown = true;
				}
		}
		return true;
	}



	public void registerAnimationControllers()
	{
		if(world.isRemote)
		{
			this.animationControllers.addAnimationController(sizeController);
			this.animationControllers.addAnimationController(moveController);
		}
	}

	@Override
	public AnimationControllerCollection getAnimationControllers()
	{
		return animationControllers;
	}


	@Override
	public EntityDataManager getDataManager()
	{
		return super.getDataManager();
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(SIZE, 1);
	}

	public int getSize()
	{
		return this.dataManager.get(SIZE);
	}

	public void setSize(int size)
	{
		this.dataManager.set(SIZE, size);
	}

	/**
	 * Called when the entity is attacked.
	 *
	 * @param source
	 * @param amount
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if(source.getTrueSource() instanceof EntityPlayer)
		{
			if(getSize() == 1)
			{
				setSize(2);
			}
		}
		return super.attackEntityFrom(source, amount);
	}


}