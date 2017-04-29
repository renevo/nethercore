package com.renevo.nethercore.item;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;

public class ItemBlockMeta extends ItemColored {

	private IProperty mappingProperty;

	public ItemBlockMeta(Block block) {
		super(block, true);
	}

	@Nonnull
	@Override
	public String getUnlocalizedName(@Nonnull ItemStack stack) {
		if(mappingProperty == null) {
			return super.getUnlocalizedName(stack);
		}

		IBlockState state = block.getStateFromMeta(stack.getMetadata());
		String name = state.getValue(mappingProperty).toString().toLowerCase(Locale.US);
		return super.getUnlocalizedName(stack) + "." + name;
	}

	public static void setMappingProperty(Block block, IProperty<?> property) {
		((ItemBlockMeta) Item.getItemFromBlock(block)).mappingProperty = property;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(@Nonnull ItemStack stack, @Nonnull EntityPlayer playerIn, @Nonnull List<String> tooltip, boolean advanced) {
		tooltip.addAll(getTooltips(
				TextFormatting.GRAY.toString() +
					I18n.format(this.getUnlocalizedName(stack) + ".tooltip"))
			);
		}

	@SideOnly(Side.CLIENT)
	public void registerItemModels() {
		final Item item = this;
		final ResourceLocation loc = GameData.getBlockRegistry().getNameForObject(block);


		for(Comparable o : (Collection<Comparable>) mappingProperty.getAllowedValues()) {
			int meta = block.getMetaFromState(block.getDefaultState().withProperty(mappingProperty, o));
			String name = mappingProperty.getName(o);

			ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(loc, mappingProperty
				.getName() + "=" + name));
		}
	}

	private static List<String> getTooltips(String text) {
		List<String> list = Lists.newLinkedList();
		if(text == null)
			return list;
		int j = 0;
		int k;
		while((k = text.indexOf("\\n", j)) >= 0)
		{
			list.add(text.substring(j, k));
			j = k+2;
		}

		list.add(text.substring(j, text.length()));

		return list;
	}
}
