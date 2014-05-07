package com.electronic.communicate;

import java.io.Serializable;

import android.graphics.Color;

public class Position implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float startX;
	private float startY;
	private float endX;
	private float endY;
	private int type;
	private int color;
	
	
	public Position() {
		super();

	}
	
	public Position(float startX, float startY, float endX, float endY,
			int type, int color) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.type = type;
		this.color = color;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public float getStartX() {
		return startX;
	}
	public void setStartX(float startX) {
		this.startX = startX;
	}
	public float getStartY() {
		return startY;
	}
	public void setStartY(float startY) {
		this.startY = startY;
	}
	public float getEndX() {
		return endX;
	}
	public void setEndX(float endX) {
		this.endX = endX;
	}
	public float getEndY() {
		return endY;
	}
	public void setEndY(float endY) {
		this.endY = endY;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	
	

}
