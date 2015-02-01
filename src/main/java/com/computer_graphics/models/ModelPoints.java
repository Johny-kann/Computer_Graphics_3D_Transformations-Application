package com.computer_graphics.models;

import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import Jama.Matrix;

/**
 * @author Janakiraman
 * 
 * class that is used to convert the data points into matrix format
 */
public class ModelPoints {

	double[] px = {0,0,-100,100,0};
	double[] py = {0,100,100,100,100};
	double[] pz = {0,-100,0,0,100};
	double[] p1 = {1,1,1,1,1};
	public int[] faces;
	public int[] faces2 = { 0,0,  2,0,  1,0,          // Front left face
	        0,0,  1,0,  3,0,          // Front right face
	        0,0,  3,0,  4,0,          // Back right face
	        0,0,  4,0,  2,0,          // Back left face
	        4,0,  1,0,  2,0,          // Bottom rear face
	        4,0,  3,0,  1,0    };
	
	public Matrix getMatrixPoints()
	{
		Matrix mat = new Matrix(new double[][]{px,py,pz,p1});
		return mat;    
	}
//	public Matrix 
	
	public Matrix getMatrixTheObject(MeshView mesh)
	{
		TriangleMesh tria = (TriangleMesh) mesh.getMesh();
		float[] points=new float[tria.getPoints().size()];
		faces = new int[tria.getFaces().size()];
		
		tria.getPoints().toArray(points);
		tria.getFaces().toArray(faces);
		
		System.out.println(points.length);
		
		
		double[] p1 = new double[points.length/3];
		double[] p2= new double[points.length/3];
		double[] p3= new double[points.length/3];
		double[] p4= new double[points.length/3];
		
		for(int i=0;i<points.length/3;i++)
		{
			p1[i]=points[3*i];
			p2[i]=points[3*i+1];
			p3[i]=points[3*i+2];
			p4[i]=1;
		}
		
		return new Matrix(new double[][]{p1,p2,p3,p4});
		
	}
}
