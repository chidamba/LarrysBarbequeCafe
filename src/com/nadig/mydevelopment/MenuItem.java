package com.nadig.mydevelopment;

import java.math.BigDecimal;

public class MenuItem
{
	public BigDecimal price;
	public String item;
	
	public MenuItem(String item, BigDecimal price)
	{
		this.price=price;
		this.item=item;
	}

}
