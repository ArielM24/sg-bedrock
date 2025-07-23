package com.sg.bedrock;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SGBedrock implements ModInitializer {
	public static final String MOD_ID = "sg-bedrock";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		UseBlockCallback.EVENT.register((player, world, hand, result) -> {
			ItemStack itemInHand = player.getStackInHand(hand);
			String interactingBlockName = world.getBlockState(result.getBlockPos()).getBlock().getName().getString();
			boolean isUsingDragonBreath = itemInHand.getItem().getName().getString()
					.equals(Items.DRAGON_BREATH.getName().getString());
			boolean isInteractingWithBedrock = interactingBlockName.equals(Blocks.BEDROCK.getName().getString());
			if(!isInteractingWithBedrock || !isUsingDragonBreath){
			return ActionResult.PASS;
				
			}
			world.breakBlock(result.getBlockPos(), false);
			player.increaseStat(Stats.USED.getOrCreateStat(itemInHand.getItem()), 1);
			itemInHand.setCount(itemInHand.getCount() - 1);
			player.giveItemStack(new ItemStack(Items.GLASS_BOTTLE));
			return ActionResult.PASS;
		});
	}
}