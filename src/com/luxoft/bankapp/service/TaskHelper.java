package com.luxoft.bankapp.service;

import java.util.Random;

public class TaskHelper {
	public static int randomInt(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}
	
	public static float randomFloat(float min, float max) {
		return new Random().nextFloat() * (max - min) + min;
	}
}
