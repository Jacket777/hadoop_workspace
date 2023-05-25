package com.demo.taglib;
import java.lang.Math;

public class DickRoller {
	public static int rollDice() {
		return (int)((Math.random()*6)+1)+10;
	}
}
