package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class NPC_Monster extends Entity{
	
	public NPC_Monster (GamePanel gp) {
		super(gp);
		
		direction= "down";
		speed = 1;
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 8;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 20;
		solidArea.height = 20;
		
		
		getImage();
				
	}
	
	public void getImage() { 
		up1 = setup("/npc/Slime_Up1");
		up2 = setup("/npc/Slime_Up2");
		down1 = setup("/npc/Slime_Down1");
		down2 = setup("/npc/Slime_Down2");
		left1 = setup("/npc/Slime_Left1");
		left2 = setup("/npc/Slime_Left2");
		right1 = setup("/npc/Slime_Right1");
		right2 = setup("/npc/Slime_Right2");
	}
	
	public void setAction() {
		
		actionLockCounter++;
		
		if(actionLockCounter == 120) {
		
		Random random = new Random();
		int i = random.nextInt(100)+1; //pick up a number from 1 to 100
		
		if(i <= 25) {
			direction = "up";
		}
		if(i > 25 && i <= 50) {
			direction = "down";
		}
		if(i > 50 && i <= 75) {
			direction = "left";
		}
		
		if(i > 75 && i <= 100) {
			direction = "right";
		}
		actionLockCounter = 0;
		}
	}
	
		
	

}
