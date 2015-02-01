package com.computer_graphics.transforms.logics;

import Jama.Matrix;

/**
 * @author Janakiraman
 * 
 * Class that contains all the transformation matrixes such as rotation,shear,scale and translate
 *
 */
public class Transformations3D {
	
	public Matrix rotationXAxis(Matrix mat,double theta)
	{
		double[] p1 = new double[]{1,0, 0,0};
		double[] p2 = new double[]{0,Math.cos(theta), -Math.sin(theta),0};
		double[] p3 = new double[]{0,Math.sin(theta), Math.cos(theta),0};
		double[] p4 = new double[]{0,0,0,1};
		
		Matrix rot = new Matrix(new double[][]{p1,p2,p3,p4});
		
		return rot.times(mat);
	}
	
	public Matrix rotationZAxis(Matrix mat,double theta)
	{
		double[] p1 = new double[]{Math.cos(theta),-Math.sin(theta), 0,0};
		double[] p2 = new double[]{Math.sin(theta), Math.cos(theta),0,0};
		double[] p3 = new double[]{0,0,1,0};
		double[] p4 = new double[]{0,0,0,1};
		
		Matrix rot = new Matrix(new double[][]{p1,p2,p3,p4});
		
		return rot.times(mat);
	}
	
	public Matrix rotationYAxis(Matrix mat,double theta)
	{
		double[] p1 = new double[]{Math.cos(theta),0,Math.sin(theta),0};
		double[] p2 = new double[]{0,1,0,0};
		double[] p3 = new double[]{-Math.sin(theta),0,Math.cos(theta),0};
		double[] p4 = new double[]{0,0,0,1};
		
		Matrix rot = new Matrix(new double[][]{p1,p2,p3,p4});
		
		return rot.times(mat);
	}
	
	public Matrix translation(Matrix mat,double tx,double ty,double tz)
	{
		double[] p1 = new double[]{1,0,0,tx};
		double[] p2 = new double[]{0,1,0,ty};
		double[] p3 = new double[]{0,0,1,tz};
		double[] p4 = new double[]{0,0,0,1};
		
		Matrix trans = new Matrix(new double[][]{p1,p2,p3,p4});
		
		return trans.times(mat);
	}
	
	public Matrix shear(Matrix mat,double shearX,double shearY,double shearZ)
	{
		double[] p1 = new double[]{1,shearX,0,0};
		double[] p2 = new double[]{shearY,1,0,0};
		double[] p3 = new double[]{0,shearZ,1,0};
		double[] p4 = new double[]{0,0,0,1};
		
		Matrix trans = new Matrix(new double[][]{p1,p2,p3,p4});
		
		return trans.times(mat);
	}
	
		
	public Matrix Scale(Matrix mat,double sx,double sy,double sz)
	{
		double[] p1 = new double[]{sx,0,0,0};
		double[] p2 = new double[]{0,sy,0,0};
		double[] p3 = new double[]{0,0,sz,0};
		double[] p4 = new double[]{0,0,0,1};
		
		Matrix trans = new Matrix(new double[][]{p1,p2,p3,p4});
		
		return trans.times(mat);
	}
	
		
	
}
