package com.computer_graphics.controller.gui;



import java.net.URL;



import org.junit.experimental.theories.DataPoint;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import Jama.Matrix;

import com.computer_graphics.models.DataModels;
import com.computer_graphics.models.ModelPoints;
import com.computer_graphics.transforms.logics.Transformations3D;
import com.computer_graphics.transforms.logics.Xform;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.TriangleMesh;

public class CanvasController {

	final Group root = new Group();
    final Xform world = new Xform();
    
   
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final Xform cameraXform = new Xform();
    final Xform cameraXform2 = new Xform();
    final Xform cameraXform3 = new Xform();
    final double cameraDistance = 450;
    final Group axisGroup = new Group();
    final Xform moleculeGroup = new Xform();
    
    
    private Timeline timeline;
    boolean timelinePlaying = false;
    double ONE_FRAME = 1.0/24.0;
    double DELTA_MULTIPLIER = 200.0;
    double CONTROL_MULTIPLIER = 0.1;
    double SHIFT_MULTIPLIER = 0.1;
    double ALT_MULTIPLIER = 0.5;
        
    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;
    
    
    @FXML
    private Slider translateZSlider;

    @FXML
    private Slider translateYSlider;

    @FXML
    private Slider scaleYSlider;

    @FXML
    private Slider translateXSlider;

    @FXML
    private Slider scaleZSlider;

    @FXML
    private Slider rotationZSlider;

    @FXML
    private Slider txslider;

    @FXML
    private Slider rotationSlider;

    @FXML
    private Slider rotationYslider;

    @FXML
    private Slider tyslider;
    
    @FXML
    private Slider tzslider;
    
    @FXML
    private StackPane topPane;
    
    @FXML
    private SubScene scene;

    @FXML
    private StackPane mainPane;
	   
	    @FXML
	    private Slider scaleXSlider;
	    
	    @FXML
	    private ComboBox<String> shapeCombo;
	   	
	   private Double x;
	   private Double y;
	   private Matrix mat;
	  
	   
	   private Double thetaX = 0.0;
	   private Double thetaY = 0.0;
	   private Double thetaZ = 0.0;
	   
	   private Double shearX = 0.0;
	   private Double shearY = 0.0;
	   private Double shearZ = 0.0;
	   
	   private Double tx = 0.0;
	   private Double ty = 0.0;
	   private Double tz = 0.0;
	   
	   private Double scaleX = 1.0;
	   private Double scaleY = 1.0;
	   private Double scaleZ = 1.0;
	   MeshView pyramid;
	   DataModels ml;
	    
   	 @FXML
     void initialize() {
   	
   		ObservableList<String> list = FXCollections.observableArrayList(
   				"Pyramid",
   				"Cow"
   				);
   		shapeCombo.setItems(list);
   		
  
   		scene = new SubScene(root, 1024, 768);
   		pyramid = new MeshView();

   		ml = new DataModels();
   		ModelPoints model = new ModelPoints();   		
   		
    //	mat = model.getMatrixPoints();
   	
    	buildScene();
        buildCamera();
        buildAxes();
//        buildMolecule();
       
        
        ObjModelImporter obj = new ObjModelImporter();
		URL location = this.getClass().getResource("/sonyericsson.obj");
		

		obj.read(location);
		MeshView pyram = obj.getImport()[0];
		
		mat = model.getMatrixTheObject(pyram);
	//	System.out.println(model.faces.length);
		ml.setFaces(model.faces);

		
		Transformations3D tr = new Transformations3D();
		mat = tr.Scale(mat, 20, 20, 20);
		buildCube(mat);
	
        setCamera(scene);
        handleMouse(scene, root);
        mainPane.getChildren().add(scene);
        scene.heightProperty().bind(mainPane.heightProperty());
   		scene.widthProperty().bind(mainPane.widthProperty());
   
    	plotPoint();
   	 }
   	  
   	 
   	@FXML
    void selectValue(ActionEvent event) {

   		ModelPoints model = new ModelPoints();   	
   		if(shapeCombo.getValue()=="Pyramid")
   		{
   			mat = model.getMatrixPoints();
   		ml.setFaces(model.faces2);
   		buildCube(mat);
   			
   		}else
   		{
   			ObjModelImporter obj = new ObjModelImporter();
   			URL location = this.getClass().getResource("/cow.obj");
   			

   			obj.read(location);
   			MeshView pyram = obj.getImport()[0];
   			
   			mat = model.getMatrixTheObject(pyram);
   			ml.setFaces(model.faces);
   			Transformations3D tr = new Transformations3D();
   			mat = tr.Scale(mat, 20, 20, 20);
   			buildCube(mat);
   		}
   				
    }
   	
	    @FXML
	    void translate(ActionEvent event) {

	    	rotateX(30.0*3.14/180);
	    	
	    }
	    
	    @FXML
	    void fillshape(ActionEvent event) {

	    	
	    	if(pyramid.getDrawMode()==DrawMode.FILL)
	    		pyramid.setDrawMode(DrawMode.LINE);
	    	else
	    		pyramid.setDrawMode(DrawMode.FILL);
	    }
	    
	  public void rotateX(double theta)
	  {
		  Transformations3D tras = new Transformations3D();
		  mat = tras.rotationXAxis(mat, theta*3.14/180);
//		  transform.drawPolygon(canvas.getGraphicsContext2D(), mat, x, y);
		  buildCube(mat);
		  
	  }
	  
	  public void rotateY(double theta)
	  {
		  Transformations3D tras = new Transformations3D();
		  mat = tras.rotationYAxis(mat, theta*3.14/180);
//		  transform.drawPolygon(canvas.getGraphicsContext2D(), mat, x, y);
		  buildCube(mat);
		  
	  }
	  
	  public void rotateZ(double theta)
	  {
		  Transformations3D tras = new Transformations3D();
		  mat = tras.rotationZAxis(mat, theta*3.14/180);
//		  transform.drawPolygon(canvas.getGraphicsContext2D(), mat, x, y);
		  buildCube(mat);
		  
	  }
	  
	  public void scale(double sx,double sy,double sz)
	  {
		  Transformations3D tras = new Transformations3D();
		  mat = tras.Scale(mat, sx, sy, sz);
		  buildCube(mat);
	  }
	  
	  public void shear(double sx,double sy,double sz)
	  {
		  Transformations3D tras = new Transformations3D();
		  mat = tras.shear(mat, sx, sy, sz);
		  buildCube(mat);
	  }
		
	  public void translate(double tx,double ty,double tz)
	  {
		  Transformations3D tras = new Transformations3D();
		  mat = tras.translation(mat, tx, ty, tz);
//		  transform.drawPolygon(canvas.getGraphicsContext2D(), mat, x, y);
		  buildCube(mat);
	  }
	  
	  public void setCamera(SubScene scene)
	  {
		  scene.setCamera(camera);
		  handleMouse(scene, root);
		  
		  
	  }
	  
	  private void buildScene() {
	        System.out.println("buildScene");
	        root.getChildren().add(world);
	    }
	 
	    private void buildCamera() {
	        root.getChildren().add(cameraXform);
	        cameraXform.getChildren().add(cameraXform2);
	        cameraXform2.getChildren().add(cameraXform3);
	        cameraXform3.getChildren().add(camera);
	        cameraXform3.setRotateZ(180.0);
	 
	        camera.setNearClip(0.1);
	        camera.setFarClip(10000.0);
	        camera.setTranslateZ(-cameraDistance);
	        cameraXform.ry.setAngle(320.0);
	        cameraXform.rx.setAngle(40);
	    }
	    
	    private void buildAxes() {
	        System.out.println("buildAxes()");
	        final PhongMaterial redMaterial = new PhongMaterial();
	        redMaterial.setDiffuseColor(Color.DARKRED);
	        redMaterial.setSpecularColor(Color.RED);
	 
	        final PhongMaterial greenMaterial = new PhongMaterial();
	        greenMaterial.setDiffuseColor(Color.DARKGREEN);
	        greenMaterial.setSpecularColor(Color.GREEN);
	 
	        final PhongMaterial blueMaterial = new PhongMaterial();
	        blueMaterial.setDiffuseColor(Color.DARKBLUE);
	        blueMaterial.setSpecularColor(Color.BLUE);
	 
	        final Box xAxis = new Box(240.0, 1, 1);
	        final Box yAxis = new Box(1, 240.0, 1);
	        final Box zAxis = new Box(1, 1, 240.0);
	        
	        xAxis.setMaterial(redMaterial);
	        yAxis.setMaterial(greenMaterial);
	        zAxis.setMaterial(blueMaterial);
	 
	        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
	        world.getChildren().addAll(axisGroup);
	    }
	    
	    
	    
	  
	  private void buildCube(Matrix matrix)
	    {
	TriangleMesh pyramidMesh = new TriangleMesh();
	    	pyramidMesh.getTexCoords().addAll(0,0);
	    	
	    	final PhongMaterial bluestuff = new PhongMaterial();
	        bluestuff.setDiffuseColor(Color.DARKBLUE);
	        bluestuff.setSpecularColor(Color.BLUE);
	    	
	   
	    	float[] aa = new float[(matrix.getRowDimension()-1)*matrix.getColumnDimension()];
	    	
	    	for(int i=0;i<matrix.getColumnDimension();i++)
	    	{
	    		aa[3*i] = (float)matrix.get(0, i);
	    		aa[3*i+1] = (float)matrix.get(1, i);
	    		aa[3*i+2] = (float)matrix.get(2, i);
	    	}
	    	pyramidMesh.getPoints().addAll(
	    	aa);
	
	    	pyramidMesh.getFaces().addAll(ml.faces
	  	    ); 
	    	
	    	try
	    	{
	    	pyramid.setMesh(pyramidMesh);

	    		
	    	}catch(Exception e)
	    	{
	    		pyramid = new MeshView(pyramidMesh);
	    	}
	    	

	    	pyramid.setMaterial(bluestuff);
	
	    	try
	    	{
	    		world.getChildren().set(1,pyramid);
	    	}catch(Exception e)
	    	{
	    		world.getChildren().add(1, pyramid);
	    		
	    	}
	    	
	  
	    }
	 
	  
	 
	  private void handleMouse(SubScene scene, final Node root) {
	        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
	            @Override public void handle(MouseEvent me) {
	                mousePosX = me.getSceneX();
	                mousePosY = me.getSceneY();
	                mouseOldX = me.getSceneX();
	                mouseOldY = me.getSceneY();
	            }
	        });
	        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
	            @Override public void handle(MouseEvent me) {
	                mouseOldX = mousePosX;
	                mouseOldY = mousePosY;
	                mousePosX = me.getSceneX();
	                mousePosY = me.getSceneY();
	                mouseDeltaX = (mousePosX - mouseOldX); 
	                mouseDeltaY = (mousePosY - mouseOldY); 
	                
	                double modifier = 1.0;
	                double modifierFactor = 0.1;
	                
	                if (me.isControlDown()) {
	                    modifier = 0.1;
	                } 
	                if (me.isShiftDown()) {
	                    modifier = 10.0;
	                }     
	                if (me.isPrimaryButtonDown()) {
	                    cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX*modifierFactor*modifier*2.0);  // +
	                    cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY*modifierFactor*modifier*2.0);  // -
	                }
	                else if (me.isSecondaryButtonDown()) {
	                    double z = camera.getTranslateZ();
	                    double newZ = z + mouseDeltaX*modifierFactor*modifier;
	                    camera.setTranslateZ(newZ);
	                }
	                else if (me.isMiddleButtonDown()) {
	                    cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX*modifierFactor*modifier*0.3);  // -
	                    cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY*modifierFactor*modifier*0.3);  // -
	                }
	            }
	        });
	    }
	  
	   void plotPoint() {

	    	
		rotationSlider.valueProperty().addListener(new ChangeListener<Number>() {
	            public void changed(ObservableValue<? extends Number> ov,
	                    Number old_val, final Number new_val) {
	               
	            	Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							rotateX(new_val.doubleValue() - thetaX);		
							thetaX = new_val.doubleValue();
						}
					});
	              }
	        });
		
		rotationYslider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, final Number new_val) {
               
            	Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						rotateY(new_val.doubleValue() - thetaY);		
						thetaY = new_val.doubleValue();
					}
				});
              }
        });
		
		rotationZSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, final Number new_val) {
               
            	Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						rotateZ(new_val.doubleValue() - thetaZ);		
						thetaZ = new_val.doubleValue();
					}
				});
              }
        });
			
			
			scaleXSlider.valueProperty().addListener(new ChangeListener<Number>() {
	            public void changed(ObservableValue<? extends Number> ov,
	                    Number old_val, final Number new_val) {
	               
	            	Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							scale(new_val.doubleValue() / scaleX,1,1);		
							scaleX = new_val.doubleValue();
						}
					});
	            	  }
	            });
			
		scaleYSlider.valueProperty().addListener(new ChangeListener<Number>() {
	            public void changed(ObservableValue<? extends Number> ov,
	                    Number old_val, final Number new_val) {
	               
	            	Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							scale(1,new_val.doubleValue() / scaleY,1);		
							scaleY = new_val.doubleValue();
						}
					});
	            	  }
	            });
			
		scaleZSlider.valueProperty().addListener(new ChangeListener<Number>() {
	            public void changed(ObservableValue<? extends Number> ov,
	                    Number old_val, final Number new_val) {
	               
	            	Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							scale(1,1,new_val.doubleValue() / scaleZ);		
							scaleZ = new_val.doubleValue();
						}
					});
	            	  }
	            });
			
				
		tzslider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, final Number new_val) {
               
            	Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						shear(0,0,new_val.doubleValue() - shearZ);		
						shearZ = new_val.doubleValue();
					}
				});
            	
            	
                  }
            });
		
			tyslider.valueProperty().addListener(new ChangeListener<Number>() {
	            public void changed(ObservableValue<? extends Number> ov,
	                    Number old_val, final Number new_val) {
	               
	            	Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							shear(0,new_val.doubleValue() - shearY,0);		
							shearY = new_val.doubleValue();
						}
					});
	            	
	            	
	                  }
	            });
			
			txslider.valueProperty().addListener(new ChangeListener<Number>() {
	            public void changed(ObservableValue<? extends Number> ov,
	                    Number old_val, final Number new_val) {
	               
	            	Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							shear(new_val.doubleValue() - shearX,0,0);		
							shearX = new_val.doubleValue();
						}
					});
	            	
	            	
	                  }
	            });
		
			translateXSlider.valueProperty().addListener(new ChangeListener<Number>() {
	            public void changed(ObservableValue<? extends Number> ov,
	                    Number old_val, final Number new_val) {
	               
	            	Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							translate(new_val.doubleValue() - tx,0,0);		
							tx = new_val.doubleValue();
						}
					});
	            	
	            	
	                  }
	            });

			translateYSlider.valueProperty().addListener(new ChangeListener<Number>() {
	            public void changed(ObservableValue<? extends Number> ov,
	                    Number old_val, final Number new_val) {
	               
	            	Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							translate(0,new_val.doubleValue() - ty,0);		
							ty = new_val.doubleValue();
						}
					});
	            	
	            	
	                  }
	            });

			translateZSlider.valueProperty().addListener(new ChangeListener<Number>() {
	            public void changed(ObservableValue<? extends Number> ov,
	                    Number old_val, final Number new_val) {
	               
	            	Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							translate(0,0,new_val.doubleValue() - tz);		
							tz = new_val.doubleValue();
						}
					});
	            	
	            	
	                  }
	            });

	   }
	            
}
			
	