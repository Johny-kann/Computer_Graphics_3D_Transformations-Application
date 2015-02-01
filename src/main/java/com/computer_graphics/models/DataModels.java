package com.computer_graphics.models;

import java.util.Arrays;

import Jama.Matrix;

/**
 * @author Janakiraman
 *
 *Class that is used to load the data of the matrix
 */
public class DataModels {

	public Matrix mat;
	public int[] faces;
	
	public void setFaces(int[] a)
	{
		faces = new int[a.length];
		faces = a;
		System.out.println("Faces "+faces.length);
	}
	
}
