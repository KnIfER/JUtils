/*
 * Copyright (c) 2008, 2017, Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package audioclip;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ffmpeg.CMN;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class AudioClipApp extends Application {
	String[] keyorder = new String[] {"Z", "X", "C", "V", "B", "N", "M", "COMMA", "PERIOD", "SLASH"};
    public Parent createContent() {
        final double xStart = 12;
        final double xOffset = 30.0;
        final double barWidth = 22.0;

        Rectangle r1 = new Rectangle(0, 15, barWidth * 11.5, 10);
        r1.setFill(new Color(0.2, 0.12, 0.1, 1.0));
        Rectangle r2 = new Rectangle(0, -25, barWidth * 11.5, 10);
        r2.setFill(new Color(0.2, 0.12, 0.1, 1.0));

        final Group content = new Group(
                r1,
                r2,
                createKey(Color.PURPLE, xStart + 0 * xOffset, barWidth, 0),
                createKey(Color.BLUEVIOLET, xStart + 1 * xOffset, barWidth, 1),
                createKey(Color.BLUE, xStart + 2 * xOffset, barWidth, 2),
                createKey(Color.GREEN, xStart + 3 * xOffset, barWidth, 3),
                createKey(Color.GREENYELLOW, xStart + 4 * xOffset, barWidth, 4),
                createKey(Color.YELLOW, xStart + 5 * xOffset, barWidth, 5),
                createKey(Color.ORANGE, xStart + 6 * xOffset, barWidth, 6),
                createKey(Color.RED, xStart + 7 * xOffset, barWidth, 7));

        // A StackPane by default centers its children, here we extend it to
        // scale the content to fill the StackPane first.
        StackPane root = new StackPane() {
            @Override protected void layoutChildren() {
                // find biggest scale that will fit while keeping proportions
            	CMN.Log(content.getBoundsInLocal().getWidth(),content.getBoundsInLocal().getHeight());
                double scale = Math.min(
                    (getWidth()-20) / content.getBoundsInLocal().getWidth(),
                    (getHeight()-20) / content.getBoundsInLocal().getHeight()
                );
                content.setScaleX((getWidth()-20) / content.getBoundsInLocal().getWidth());
                content.setScaleY((getHeight()-20) / content.getBoundsInLocal().getHeight());
                super.layoutChildren();
            }
        };
        root.getChildren().add(content);
        return root;
    }

    

    
    
    static ArrayList<AudioClip> mAudioClips = new ArrayList<>();
    public Rectangle createKey(Color color, double x,
                                      double width, int note) {

        double height = 100 - ((note) * 5);
        // create a audio clip that this key will play
        final AudioClip barNote = new AudioClip( AudioClipApp.class.getResource("/shared-resources/"+"Note"+(note+1)+".wav").toExternalForm());
        mAudioClips.add(barNote);
        // create the rectangle that draws the key
        Rectangle rectangle = new Rectangle(x, -(100 / 2), width, 100);
        rectangle.setFill(color);
        Lighting lighting = new Lighting(new Light.Point(-20, -20, 100, Color.WHITE));
        lighting.setSurfaceScale(1);
        rectangle.setEffect(lighting);
        rectangle.setOnMousePressed((MouseEvent me) -> {
        	processKeyEvent(note,0,keyorder[note]);
        });
        rectangle.setOnMouseReleased((MouseEvent me) -> {
        	processKeyEvent(note,1,keyorder[note]);
        });
        return rectangle;
    }

    
    static HashSet<Integer> keypools=new HashSet<>();
    Stage main_stage;
	protected Thread playback_thread;
    @Override
    public void start(Stage stage) throws Exception {
    	int width=1250;
		int height=810;
		int tmp = -1;
		boolean alwaysOnTop = false;
		tmp = Configs.getLonelyInteger(new File(usrHome,"width"));
		if(tmp!=-1) width=tmp;
		tmp = Configs.getLonelyInteger(new File(usrHome,"height"));
		if(tmp!=-1) height=tmp;
		tmp = Configs.getLonelyInteger(new File(usrHome,"posX"));
		if(tmp!=-1) stage.setX(tmp);
		tmp = Configs.getLonelyInteger(new File(usrHome,"posY"));
		if(tmp!=-1) stage.setY(tmp);
		alwaysOnTop=new File(usrHome,"posY").exists();
		
    	Scene scene = new Scene(createContent(), width, height);
    	main_stage=stage;
    	stage.setScene(scene);
        stage.show();
        stage.setTitle("Plain Music Inputer");
        stage.setAlwaysOnTop(alwaysOnTop);
		stage.setOnHidden(e -> {
			dumpSettings();
			Platform.exit();
			});
		
        EventHandler<? super KeyEvent> mEventHandler = new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
            	
            	int index=Configs.getKeyMappingIndex(event.getCode());
            	switch(event.getCode()) {
            		case V://粘贴
            			if(event.isControlDown() && event.getEventType()==KeyEvent.KEY_PRESSED) {
							Clipboard clipboard = Clipboard.getSystemClipboard();
							String pasted = clipboard.getString();
            				playback_thread = new Thread(new Runnable() {
        						long startT=-1;
        						long startTime=-1;
								@Override
								public void run() {
		            				//CMN.Log();
		            				Pattern p = Pattern.compile("\\[([0-9]{1,})\\] (:?[A-Z]{1,})");
		            				Matcher m = p.matcher(pasted);
	            					CMN.Log();
		            				while(m.find()) {
		            					long time = Long.valueOf(m.group(1));
		            					if(startT==-1)
		            						startT=time;
		            					time-=startT;
		            					long currentTime = System.currentTimeMillis();
		            					if(startTime==-1)
		            						startTime=currentTime;
		            					currentTime-=startTime;
		            					

		            					String code = m.group(2);
		            					int index2=Configs.getKeyMappingIndex(KeyCode.valueOf(code));
		            					
		            					if(currentTime<time) {
		            						try {
												Thread.sleep(time-currentTime-1);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
		            					}
		            					CMN.Log2(code," ");
		            					mAudioClips.get(index2).play();
		            				}
	            					CMN.Log();
								}});
            				playback_thread.start(); 
            				return;
            			}
        			break;
            		case W:
            			if(event.isControlDown()) {
            				dumpSettings();
            				Platform.exit();
            				return;
            			}
        			break;
            		case ENTER:
            			if(event.getEventType()==KeyEvent.KEY_PRESSED) {
            				CMN.Log();
            				return;
            			}
        			break;
            		case F1:
            			if(event.getEventType()==KeyEvent.KEY_PRESSED)
            				main_stage.setAlwaysOnTop(!main_stage.isAlwaysOnTop());
        			break;
            		case F9:
            			if(event.getEventType()==KeyEvent.KEY_PRESSED)
            				main_stage.setAlwaysOnTop(!main_stage.isAlwaysOnTop());
        			break;
            	}
            	
            	//CMN.Log(index,event.getCode(),event.getEventType().getName());//logkey
            	int mEventType=-1;
            	mEventType=event.getEventType()==KeyEvent.KEY_PRESSED?0:event.getEventType()==KeyEvent.KEY_RELEASED?1:-1;

            	processKeyEvent(index,mEventType,event.getCode());
            }
        };
        scene.setOnKeyPressed(mEventHandler);
		scene.setOnKeyReleased(mEventHandler);
		scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event)
	        {
	            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2)
	            {
	            	
	            }
	            if(event.getButton() == MouseButton.SECONDARY)
	            {
	            	KeyEvent paste_event = new KeyEvent(KeyEvent.KEY_PRESSED, "V", "V", KeyCode.V, false, true, false, false);
	            	mEventHandler.handle(paste_event);
	            }               
	        }
	    });
        
    }
    



    private void dumpSettings() {
    	if(!main_stage.isMaximized() && !main_stage.isIconified()) {
			Configs.dumpLonelyInteger(new File(usrHome,"width"),(int)main_stage.getWidth());
			Configs.dumpLonelyInteger(new File(usrHome,"height"),(int)main_stage.getHeight());
			Configs.dumpLonelyInteger(new File(usrHome,"posX"),(int)main_stage.getX());
			Configs.dumpLonelyInteger(new File(usrHome,"posY"),(int)main_stage.getY());
		}
		if(main_stage.isAlwaysOnTop())
			try {
				new File(usrHome,"posY").createNewFile();
			} catch (IOException e1) {}
		else new File(usrHome,"posY").delete();
	}




	protected void processKeyEvent(int index, int mEventType, Object keycode) {
    	if(index!=-1) {
    		if(start_time==null) {
    			start_time = System.currentTimeMillis();
    		}
        	switch(mEventType) {
        		case 0:
	    			if(!keypools.contains(index)) {
	        			CMN.Log2("[",System.currentTimeMillis()-start_time,"]"," ",keycode," ");
	    				mAudioClips.get(index).play();
	        			keypools.add(index);
	    			}
        		break;
        		case 1:
        			keypools.remove(index);
        		break;
        	}
    	}
	}




	//构造
    public AudioClipApp() {
        super();
    	if(!new File(projectPath).exists()) {
    		
    	}
    	if(userPath==null || !new File(userPath).exists()) {
    		userPath=projectPath;
    	}
    	usrHome = new File(userPath,".PLOD.Audioclips");
    	usrHome.mkdir();
    	userPath=null;

    }
    
    
    /**
     * Java main for when running without JavaFX launcher
     * @param args command line arguments
     */
    Long start_time;
	File usrHome;
	public static String projectPath;
	public static String userPath;
	static boolean isNeoJRE;
    public static void main(String[] args) { 
    	projectPath = PU.getProjectPath();
		userPath = System.getProperty("user.home");
		CMN.Log("projectPath"+projectPath);
		String VersionCode = System.getProperty("java.version");
		if(VersionCode.startsWith("9") || VersionCode.startsWith("10"))
			isNeoJRE=true;
    	launch(args); 
    }
}
